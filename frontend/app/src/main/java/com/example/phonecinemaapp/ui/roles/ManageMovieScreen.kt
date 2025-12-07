package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.phonecinemaapp.data.repository.PeliculasRepositoryRemote
import com.example.phonecinemaapp.ui.theme.PhoneCinemaBlue
import com.example.phonecinemaapp.ui.theme.PhoneCinemaYellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageMovieScreen(
    peliculasRepository: PeliculasRepositoryRemote, // <-- Recibe el repositorio
    onNavigateBackPanel: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var movies by remember { mutableStateOf<List<MoviePlaceholder>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    // Cargar películas cuando se abre la pantalla
    LaunchedEffect(Unit) {
        // Por ahora usa datos de ejemplo, después puedes usar:
        // movies = peliculasRepository.getAll().map { it.toPlaceholder() }
        movies = getSampleMovies()
        isLoading = false
    }

    Scaffold(
        containerColor = PhoneCinemaBlue,
        topBar = {
            TopAppBar(
                title = {
                    Text("Gestión de Películas", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PhoneCinemaYellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
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
            // Barra de búsqueda
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar películas") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = PhoneCinemaYellow,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = PhoneCinemaYellow,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = PhoneCinemaYellow)
                }
            )

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PhoneCinemaYellow)
                }
            } else {
                // Filtrar películas por búsqueda
                val filteredMovies = if (searchQuery.isBlank()) {
                    movies
                } else {
                    movies.filter {
                        it.title.contains(searchQuery, ignoreCase = true) ||
                                it.genre.contains(searchQuery, ignoreCase = true)
                    }
                }

                // Lista de películas
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (filteredMovies.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "No hay películas",
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        items(filteredMovies) { movie ->
                            MovieManagementItem(
                                movie = movie,
                                onEdit = {
                                    // TODO: Abrir diálogo de edición cuando tengas API
                                },
                                onDelete = {
                                    scope.launch {
                                        // TODO: Implementar cuando tengas API
                                        // peliculasRepository.delete(movie.id)
                                        movies = movies.filter { it.id != movie.id }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Diálogo para agregar película (placeholder)
    if (showAddDialog) {
        AddMovieDialogPlaceholder(
            onDismiss = { showAddDialog = false },
            onSave = { newMovie ->
                scope.launch {
                    // TODO: Implementar cuando tengas API
                    // val saved = peliculasRepository.save(newMovie.toPeliculaRemote())
                    movies = movies + newMovie
                }
                showAddDialog = false
            }
        )
    }
}

// Data class para películas (placeholder - cambiar por PeliculaRemote cuando tengas API)
data class MoviePlaceholder(
    val id: Int,
    val title: String,
    val genre: String,
    val duration: Int,
    val year: Int,
    val description: String = ""
)

// Función de ejemplo - remover cuando tengas API
private fun getSampleMovies(): List<MoviePlaceholder> {
    return listOf(
        MoviePlaceholder(
            id = 1,
            title = "El Padrino",
            genre = "Drama/Crimen",
            duration = 175,
            year = 1972,
            description = "La historia de la familia mafiosa Corleone"
        ),
        MoviePlaceholder(
            id = 2,
            title = "Interestelar",
            genre = "Ciencia Ficción",
            duration = 169,
            year = 2014,
            description = "Viaje a través de un agujero de gusano"
        ),
        MoviePlaceholder(
            id = 3,
            title = "El Señor de los Anillos",
            genre = "Fantasía/Aventura",
            duration = 178,
            year = 2001,
            description = "Un hobbit destruye un anillo poderoso"
        )
    )
}

@Composable
fun MovieManagementItem(
    movie: MoviePlaceholder,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de película
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(PhoneCinemaYellow.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Movie,
                    contentDescription = "Película",
                    tint = PhoneCinemaYellow,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información de la película
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${movie.genre} • ${movie.duration} min • ${movie.year}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Botones de acción
            Row {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = PhoneCinemaYellow
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFB23A48)
                    )
                }
            }
        }
    }
}

// Diálogo placeholder simple
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieDialogPlaceholder(
    onDismiss: () -> Unit,
    onSave: (MoviePlaceholder) -> Unit
) {
    var title by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Película") },
        text = {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newMovie = MoviePlaceholder(
                        id = 0, // Temporal
                        title = title,
                        genre = "",
                        duration = 0,
                        year = 0
                    )
                    onSave(newMovie)
                },
                enabled = title.isNotBlank()
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}