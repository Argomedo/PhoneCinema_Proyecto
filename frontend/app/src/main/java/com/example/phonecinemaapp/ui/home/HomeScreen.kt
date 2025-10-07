package com.example.phonecinemaapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.R
import com.example.phonecinemaapp.ui.components.AppTopBar
import androidx.compose.material.icons.filled.Menu // Para el menú hamburguesa
import androidx.compose.material.icons.filled.MoreVert // Para los tres puntos
import androidx.compose.material.icons.filled.Search // Para la lupa
import androidx.compose.material.icons.outlined.Folder // Para la carpeta
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.Color // Para definir el color verde

// Ya no necesitamos los modelos de datos ni el ViewModel aquí.
// Solo importamos lo necesario para la UI.

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onLogout: () -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onLogoutClick = onLogout,
        onMovieClick = { peliculaId -> onNavigateToMovieDetails(peliculaId)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onLogoutClick: () -> Unit, // Mantenemos el parámetro por si lo necesitas
    onMovieClick: (Int) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            // Aquí está el TopAppBar modificado para verse como el de la imagen
            TopAppBar(
                title = {
                    Text("Películas") // Titulo de la barra
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD4A106), // Color del topbar
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Acción para abrir el menú lateral */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú de navegación"
                        )
                    }
                },
                actions = {
                    // Iconos de la derecha
                    IconButton(onClick = { /* TODO: Acción para buscar */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    }
                    IconButton(onClick = { /* TODO: Acción para abrir Perfil */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Person, // Usamos el Outlined para que se parezca más
                            contentDescription = "Perfil"
                        )
                    }
                    IconButton(onClick = {
                        // Icono para salir
                        /* TODO: Acción para más opciones */
                    }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Salir"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // El resto del contenido
            items(uiState.categorias) { categoria ->
                Text(
                    text = categoria.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)

                ) {
                    items(categoria.peliculas) { pelicula ->
                        PeliculaItem(
                            pelicula = pelicula,
                            onMovieClick = { onMovieClick(pelicula.id) }
                        )
                    }
                }
                Divider(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    thickness = 3.dp
                )
            }
        }
    }
}

@Composable
fun PeliculaItem(
    pelicula: Pelicula,
    onMovieClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onMovieClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(120.dp, 170.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(pelicula.posterResId),
                contentDescription = "Poster de ${pelicula.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = pelicula.nombre,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// LAS PREVIEWS SE QUEDAN AQUÍ, EN EL ARCHIVO DE LA UI

@Preview(showBackground = true)
@Composable
fun PeliculaItemPreview() {
    val peliculaDeEjemplo = Pelicula(1, "Película de Acción Súper Larga", R.drawable.ic_logo)
    PeliculaItem(pelicula = peliculaDeEjemplo, onMovieClick = {})
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenContentPreview() {
    val peliculasEjemplo = List(8) { Pelicula(it, "Película ${it + 1}", R.drawable.ic_logo) }
    val categoriasEjemplo = listOf(
        Categoria("Acción", peliculasEjemplo),
        Categoria("Comedia", peliculasEjemplo.subList(0, 4))
    )
    val uiStateEjemplo = HomeUiState(categorias = categoriasEjemplo, nombreUsuario = "PreviewUser")

    HomeScreenContent(
        uiState = uiStateEjemplo,
        onLogoutClick = {},
        onMovieClick = {}
    )
}