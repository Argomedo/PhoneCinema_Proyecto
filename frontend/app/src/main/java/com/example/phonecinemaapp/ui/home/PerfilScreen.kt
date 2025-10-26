package com.example.phonecinemaapp.ui.perfil

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.phonecinemaapp.data.local.database.AppDatabase
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.ui.components.AppTopBar
import com.example.phonecinemaapp.utils.RecuerdaFotos
import kotlinx.coroutines.delay
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    userEmail: String,
    onBackClick: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val database = AppDatabase.getInstance(context)
    val userRepository = remember { UserRepository(database.userDao()) }
    val perfilViewModel: PerfilViewModel = viewModel(factory = PerfilViewModelFactory(userRepository))
    val uiState by perfilViewModel.uiState.collectAsState()

    LaunchedEffect(userEmail) {
        perfilViewModel.cargarUsuario(userEmail)
    }

    if (uiState.isLoggedOut) {
        onLogout()
        return
    }

    PerfilContent(
        navController = navController,
        uiState = uiState,
        onBackClick = onBackClick,
        onSave = perfilViewModel::guardarCambios,
        onNombreChange = perfilViewModel::onNombreChange,
        onEmailChange = perfilViewModel::onEmailChange,
        onFotoChange = perfilViewModel::onFotoChange,
        onClearMessages = perfilViewModel::clearMessages,
        onLogout = perfilViewModel::logout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilContent(
    navController: NavController,
    uiState: PerfilUiState,
    onBackClick: () -> Unit,
    onSave: () -> Unit,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onFotoChange: (String) -> Unit,
    onClearMessages: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val fotoPerfil = RecuerdaFotos()

    var tempImageFile by remember { mutableStateOf<File?>(null) }
    var showImageSourceDialog by remember { mutableStateOf(false) }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempImageFile?.let { file ->
                val uri = fotoPerfil.ConsigueImagenUri(file)
                onFotoChange(uri.toString())
            }
        }
        tempImageFile = null
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onFotoChange(it.toString()) }
    }

    val camaraPermisionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            tempImageFile = fotoPerfil.crearArchivoImagen()
            tempImageFile?.let { file ->
                val imageUri = fotoPerfil.ConsigueImagenUri(file)
                camaraLauncher.launch(imageUri)
            }
        }
    }

    LaunchedEffect(uiState.errorMensaje, uiState.successMensaje) {
        if (uiState.errorMensaje != null || uiState.successMensaje != null) {
            delay(3000)
            onClearMessages()
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Perfil",
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogout
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            SeccionFotoPerfil(
                fotoUri = uiState.fotoUri,
                onTakePhoto = { showImageSourceDialog = true }
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            LaunchedEffect(uiState.errorMensaje, uiState.successMensaje) {
                uiState.errorMensaje?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
                uiState.successMensaje?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onSave, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Guardar Cambios",
                    color = Color(0xFF253B76)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB23A48),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cerrar Sesión", color = Color.White)
            }
        }
    }

    // --- Diálogo para Cámara o Galería ---
    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("Seleccionar imagen", color = Color.White) },
            text = { Text("¿De dónde quieres obtener la imagen?", color = Color.White) },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            showImageSourceDialog = false
                            galleryLauncher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253B76))
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Galería", color = Color(0xFFFAFAFA))
                    }

                    Button(
                        onClick = {
                            showImageSourceDialog = false
                            camaraPermisionLauncher.launch(android.Manifest.permission.CAMERA)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253B76))
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cámara", color = Color(0xFFFAFAFA))
                    }
                }
            }
        )
    }
}

// --- Cuadro para la foto de perfil ---
@Composable
fun SeccionFotoPerfil(
    fotoUri: String,
    onTakePhoto: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(Color(0xFF3949AB))
        ) {
            if (fotoUri.isNotBlank()) {
                AsyncImage(
                    model = fotoUri,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar por defecto",
                    tint = Color.White,
                    modifier = Modifier.size(72.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onTakePhoto) {
            Text(text = "Cambiar foto de perfil", color = Color(0xFF253B76))
        }
    }
}
