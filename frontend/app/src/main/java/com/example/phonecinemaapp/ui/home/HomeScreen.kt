package com.example.phonecinemaapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.R
import com.example.phonecinemaapp.data.session.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onLogout: () -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    // Filtrado simple de películas
    val filteredCategorias = uiState.categorias.map { categoria ->
        categoria.copy(
            peliculas = categoria.peliculas.filter {
                it.nombre.contains(searchQuery, ignoreCase = true)
            }
        )
    }.filter { it.peliculas.isNotEmpty() || searchQuery.isBlank() }

    LaunchedEffect(Unit) {
        val user = UserSession.currentUser
        if (user != null) homeViewModel.loadUser(user.name)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    if (!isSearching) {
                        Text("Catálogo", color = Color.White)
                    } else {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Buscar película...") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.White,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
                                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD4A106),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { isSearching = !isSearching }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar película",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Perfil",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
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
            items(filteredCategorias) { categoria ->
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
                            onMovieClick = { onNavigateToMovieDetails(pelicula.id) }
                        )
                    }
                }
                HorizontalDivider(
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
