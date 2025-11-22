package com.example.phonecinemaapp.ui.roles

import UserDto
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.ui.theme.PhoneCinemaYellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUsersScreen(
    userRepo: UserRepository,
    onNavigateBackToAdmin: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<UserDto>>(emptyList()) }

    LaunchedEffect(Unit) {
        users = runCatching { userRepo.getAllUsers() }.getOrElse { emptyList() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Usuarios", color = Color.White) },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = Color(0xFFB23A48)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PhoneCinemaYellow)
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
                Text("No hay usuarios registrados", color = Color.White)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(users) { user ->
                        UserCard(
                            user = user,
                            onBan = {
                                scope.launch {
                                    runCatching {
                                        userRepo.deleteUser(user.id)   // ← corrección
                                        users = users.filter { it.id != user.id }
                                    }
                                }
                            },
                            onToggleRole = {
                                scope.launch {
                                    runCatching {
                                        val nuevoRol = when (user.rol) {
                                            "Usuario" -> "Moderador"
                                            "Moderador" -> "Usuario"
                                            else -> "Usuario"
                                        }

                                        // Llamada correcta
                                        userRepo.updateUser(user.id, nuevoRol)

                                        // Refresca la lista visualmente
                                        users = users.map { if (it.id == user.id) it.copy(rol = nuevoRol) else it }
                                    }
                                }

                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateBackToAdmin,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PhoneCinemaYellow)
            ) {
                Text("Volver al Panel Admin", color = Color(0xFF253B76))
            }
        }
    }
}

@Composable
fun UserCard(
    user: UserDto,
    onBan: () -> Unit,
    onToggleRole: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1A3B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFFD4A106).copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {

            Text(
                text = "Nombre: ${user.nombre}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Correo: ${user.email}",
                color = Color(0xFFFFC107),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = "Rol actual: ${user.rol}",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(8.dp))

            Divider(color = Color(0xFFD4A106).copy(alpha = 0.4f))

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Acciones", color = Color.Gray)

                Row {
                    IconButton(onClick = onToggleRole) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = "Cambiar Rol", tint = Color(0xFFFFC107))
                    }
                    IconButton(onClick = onBan) {
                        Icon(Icons.Default.Block, contentDescription = "Banear Usuario", tint = Color(0xFFB23A48))
                    }
                }
            }
        }
    }
}
