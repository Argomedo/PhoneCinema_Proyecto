package com.example.phonecinemaapp.ui.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.phonecinemaapp.R
import com.example.phonecinemaapp.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val categorias = listOf("Acción", "Comedia", "Romance")
    val peliculas = List(8) { "Película $it" }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("PhoneCinema") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    actions = { //Boton para Deslogearse
                        TextButton(
                            onClick = {
                                navController.navigate(AppScreens.LoginScreen.route) {
                                    popUpTo(AppScreens.HomeScreen.route) { inclusive = true }
                                }
                            }
                        ){ //Texto del Boton para no confundirse
                            Text(
                                text = "Cerrar Sesion",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                categorias.forEach { categoria ->
                    item {
                        Text(
                            text = categoria,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 8.dp)
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(peliculas.size) { index ->
                                PeliculaItem(nombre = peliculas[index])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PeliculaItem(nombre: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nombre,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Poster de $nombre",
            modifier = Modifier.size(100.dp, 120.dp),
            contentScale = ContentScale.Crop
        )
    }
}
