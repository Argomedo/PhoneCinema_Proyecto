package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.repository.UserRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUsersScreen(
    userRepo: UserRepository,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<UserEntity>>(emptyList()) }

    // Cargar usuarios (excluye Admin)
    LaunchedEffect(Unit) {
        users = userRepo.getAllUsers().filter { it.role != "Admin" }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Usuarios") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFC107))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (users.isEmpty()) {
                Text("No hay usuarios registrados", color = Color(0xFFFAFAFA))
            } else {
                LazyColumn {
                    items(users) { user ->
                        UserCard(
                            user = user,
                            onBan = {
                                scope.launch {
                                    userRepo.deleteUser(user)
                                    users = users.filter { it.id != user.id }
                                }
                            },
                            onToggleRole = {
                                scope.launch {
                                    val nuevoRol =
                                        if (user.role == "Usuario") "Moderador" else "Usuario"
                                    val actualizado = user.copy(role = nuevoRol)
                                    userRepo.updateUser(actualizado)
                                    users = users.map {
                                        if (it.id == user.id) actualizado else it
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(
    user: UserEntity,
    onBan: () -> Unit,
    onToggleRole: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF253B76).copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(user.name, color = Color.White, style = MaterialTheme.typography.titleMedium)
                Text(user.email, color = Color(0xFFFFC107), style = MaterialTheme.typography.bodySmall)
                Text("Rol: ${user.role}", color = Color.White)
            }

            Row {
                IconButton(onClick = onToggleRole) {
                    Icon(Icons.Default.SwapHoriz, contentDescription = "Cambiar Rol", tint = Color(0xFFFFC107))
                }
                IconButton(onClick = onBan) {
                    Icon(Icons.Default.Block, contentDescription = "Banear", tint = Color.Red)
                }
            }
        }
    }
}
