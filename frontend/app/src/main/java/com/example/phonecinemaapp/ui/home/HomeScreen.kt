package com.example.phonecinemaapp.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.R
import kotlinx.coroutines.launch

// Ya no necesitamos los modelos de datos ni el ViewModel aquí.
// Solo importamos lo necesario para la UI.

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onLogout: () -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Manejar el botón de retroceso
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú principal",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Divider()

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToProfile()
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onLogout()
                    }
                )
            }
        }
    ) {
        HomeScreenContent(
            uiState = uiState,
            onLogoutClick = onLogout,
            onMovieClick = onNavigateToMovieDetails,
            onMenuClick = { scope.launch { drawerState.open() } },
            onProfileClick = {
                scope.launch { drawerState.close() }
                onNavigateToProfile()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onLogoutClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text("Películas")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD4A106),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.Menu, // Mantenemos Menu aquí
                            contentDescription = "Abrir menú de navegación"
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
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onMovieClick(pelicula.id) },
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
        onMovieClick = {},
        onMenuClick = {},
        onProfileClick = {}
    )
}

// Preview del HomeScreen completo con drawer
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    val peliculasEjemplo = List(8) { Pelicula(it, "Película ${it + 1}", R.drawable.ic_logo) }
    val categoriasEjemplo = listOf(
        Categoria("Acción", peliculasEjemplo),
        Categoria("Comedia", peliculasEjemplo.subList(0, 4))
    )
    val uiStateEjemplo = HomeUiState(categorias = categoriasEjemplo, nombreUsuario = "PreviewUser")

    // Para el preview, necesitarías un ViewModel mock o usar remember
    HomeScreen(
        onLogout = {},
        onNavigateToMovieDetails = {},
        onNavigateToProfile = {}
    )
}