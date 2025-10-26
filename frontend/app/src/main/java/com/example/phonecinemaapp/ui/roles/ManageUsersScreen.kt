package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.ui.components.AppTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUsersScreen(
    navController: NavController,
    userRepo: UserRepository,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<UserEntity>>(emptyList()) }

    // Cargar usuarios (excluye Admin)
    LaunchedEffect(Unit) {
        users = userRepo.getAllUsers().filter { it.role != "Admin" }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gestión de Usuarios",
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogoutClick
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
                Text(
                    text = "No hay usuarios registrados",
                    color = Color(0xFFFAFAFA),
                    style = MaterialTheme.typography.bodyMedium
                )
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
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF253B76).copy(alpha = 0.15f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = user.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = user.email,
                    color = Color(0xFFFFC107),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Rol: ${user.role}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row {
                IconButton(onClick = onToggleRole) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Cambiar Rol",
                        tint = Color(0xFFFFC107)
                    )
                }
                IconButton(onClick = onBan) {
                    Icon(
                        imageVector = Icons.Default.Block,
                        contentDescription = "Banear Usuario",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
