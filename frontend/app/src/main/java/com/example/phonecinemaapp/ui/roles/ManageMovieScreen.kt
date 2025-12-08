package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.phonecinemaapp.data.remote.dto.PeliculaDTO
import com.example.phonecinemaapp.data.remote.dto.PeliculaCreateDTO
import com.example.phonecinemaapp.data.repository.PeliculasRepositoryRemote
import com.example.phonecinemaapp.ui.theme.PhoneCinemaBlue
import com.example.phonecinemaapp.ui.theme.PhoneCinemaYellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageMovieScreen(
    peliculasRepository: PeliculasRepositoryRemote,
    onNavigateBackPanel: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var movies by remember { mutableStateOf<List<PeliculaDTO>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        movies = peliculasRepository.getAll()
        isLoading = false
    }

    Scaffold(
        containerColor = PhoneCinemaBlue,
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Películas", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PhoneCinemaYellow,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
                    }
                    IconButton(onClick = onLogoutClick) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión", tint = Color(0xFFB23A48))
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar por título o género") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = PhoneCinemaYellow,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PhoneCinemaYellow)
                }
            } else {
                val filtered = movies.filter {
                    searchQuery.isBlank() ||
                            it.nombre.contains(searchQuery, ignoreCase = true) ||
                            it.genero.contains(searchQuery, ignoreCase = true)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered) { movie ->
                        MovieManagementItem(
                            movie = movie,
                            onDelete = {
                                scope.launch {
                                    peliculasRepository.delete(movie.id)
                                    movies = movies.filter { it.id != movie.id }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddMovieDialog(
            onDismiss = { showAddDialog = false },
            onSave = { dto ->
                scope.launch {
                    val saved = peliculasRepository.create(dto)
                    movies = movies + saved
                }
                showAddDialog = false
            }
        )
    }
}

@Composable
fun MovieManagementItem(
    movie: PeliculaDTO,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(PhoneCinemaYellow.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Movie, contentDescription = null, tint = PhoneCinemaYellow)
            }

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    movie.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${movie.genero} • ${movie.duracion} • ${movie.anio}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFB23A48))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieDialog(
    onDismiss: () -> Unit,
    onSave: (PeliculaCreateDTO) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var posterUrl by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Película") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                TextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                TextField(value = genero, onValueChange = { genero = it }, label = { Text("Género") })
                TextField(value = duracion, onValueChange = { duracion = it }, label = { Text("Duración (ej: 2h 10m)") })
                TextField(value = anio, onValueChange = { anio = it }, label = { Text("Año") })
                TextField(value = posterUrl, onValueChange = { posterUrl = it }, label = { Text("Poster URL") })
            }
        },
        confirmButton = {
            TextButton(
                enabled = nombre.isNotBlank() && genero.isNotBlank(),
                onClick = {
                    onSave(
                        PeliculaCreateDTO(
                            nombre = nombre,
                            descripcion = descripcion,
                            genero = genero,
                            duracion = duracion,
                            anio = anio.toIntOrNull() ?: 0,
                            posterUrl = posterUrl
                        )
                    )
                }
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
