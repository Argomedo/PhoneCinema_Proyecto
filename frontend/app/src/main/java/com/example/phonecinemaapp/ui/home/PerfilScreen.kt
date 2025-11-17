package com.example.phonecinemaapp.ui.perfil

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.phonecinemaapp.data.local.database.AppDatabase
import com.example.phonecinemaapp.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.ui.components.AppTopBar
import com.example.phonecinemaapp.ui.reseñas.ReviewItem
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
    val reviewRepository = remember { ReviewRepository(database.reviewDao()) }

    val perfilViewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(userRepository, reviewRepository)
    )
    val uiState by perfilViewModel.uiState.collectAsState()

    LaunchedEffect(userEmail) {
        perfilViewModel.cargarUsuario(userEmail)
        perfilViewModel.cargarResenasUsuario(userEmail)
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
        onLogout = perfilViewModel::logout,
        onChangePassword = perfilViewModel::cambiarPassword
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
    onLogout: () -> Unit,
    onChangePassword: (String, String) -> Unit
) {
    val context = LocalContext.current
    val fotoPerfil = RecuerdaFotos()

    var tempImageFile by remember { mutableStateOf<File?>(null) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

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
                title = "Mi Perfil",
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogout
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(onClick = onSave, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "CONFIRMAR CAMBIOS")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { showChangePasswordDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "CAMBIAR CONTRASEÑA")
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
                        Text(text = "CERRAR SESIÓN", color = Color.White)
                    }
                }
            }

            item {
                if (uiState.userReviews.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mis Reseñas",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Total: ${uiState.userReviews.size}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Divider(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            if (uiState.userReviews.isNotEmpty()) {
                items(uiState.userReviews) { review ->
                    ReviewItem(
                        review = review,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            } else {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Reviews,
                            contentDescription = "Sin reseñas",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Aún no has hecho ninguna reseña",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (showChangePasswordDialog) {
        CambiarPasswordDialog(
            onDismiss = { showChangePasswordDialog = false },
            onConfirm = { nuevaPassword, confirmacion ->
                onChangePassword(nuevaPassword, confirmacion)
                showChangePasswordDialog = false
            }
        )
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("Seleccionar imagen") },
            text = { Text("¿De dónde quieres obtener la imagen?") },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            showImageSourceDialog = false
                            galleryLauncher.launch("image/*")
                        }
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Galería")
                    }

                    Button(
                        onClick = {
                            showImageSourceDialog = false
                            camaraPermisionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cámara")
                    }
                }
            }
        )
    }

    LaunchedEffect(uiState.errorMensaje, uiState.successMensaje) {
        uiState.errorMensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        uiState.successMensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun CambiarPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var nuevaPassword by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Cambiar Contraseña",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                if (showError) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = nuevaPassword,
                    onValueChange = {
                        nuevaPassword = it
                        showError = false
                    },
                    label = { Text("Nueva contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmarPassword,
                    onValueChange = {
                        confirmarPassword = it
                        showError = false
                    },
                    label = { Text("Confirmar contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nuevaPassword.isBlank() || confirmarPassword.isBlank()) {
                        showError = true
                        errorMessage = "Ambos campos son obligatorios"
                    } else if (nuevaPassword != confirmarPassword) {
                        showError = true
                        errorMessage = "Las contraseñas no coinciden"
                    } else if (nuevaPassword.length < 6) {
                        showError = true
                        errorMessage = "La contraseña debe tener al menos 6 caracteres"
                    } else {
                        onConfirm(nuevaPassword, confirmarPassword)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CONFIRMAR CAMBIO")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CANCELAR")
            }
        }
    )
}

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
            Text(text = "Cambiar foto de perfil")
        }
    }
}