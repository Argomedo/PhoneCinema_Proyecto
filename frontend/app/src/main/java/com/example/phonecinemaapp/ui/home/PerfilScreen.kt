package com.example.phonecinemaapp.ui.perfil

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.phonecinemaapp.navigation.AppScreens
import com.example.phonecinemaapp.ui.components.AppTopBar
import com.example.phonecinemaapp.utils.RecuerdaFotos
import kotlinx.coroutines.delay
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    userId: Long,
    perfilViewModel: PerfilViewModel,
    onBackClick: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToFeedback: () -> Unit
) {
    val uiState by perfilViewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        perfilViewModel.cargarUsuario(userId)
        perfilViewModel.cargarResenasUsuario(userId)
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
    val cs = MaterialTheme.colorScheme

    var tempImageFile by remember { mutableStateOf<File?>(null) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    val softWhite = Color(0xFFDDDDDD)
    val avatarBlue = Color(0xFF1A2750)
    val fieldDisabledBg = Color(0xFF2A3350)

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
                onLogoutClick = onLogout,
                onFeedbackClick = {
                    navController.navigate(AppScreens.FeedbackScreen.route)
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .background(cs.background)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {

                    SeccionFotoPerfil(
                        fotoUri = uiState.fotoUri,
                        onTakePhoto = { showImageSourceDialog = true },
                        softWhite = softWhite,
                        avatarBlue = avatarBlue
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // -------------------------------------------------
                    // CAMPOS NO EDITABLES CON FONDO DISTINTIVO
                    // -------------------------------------------------

                    OutlinedTextField(
                        value = uiState.nombre,
                        onValueChange = {},
                        enabled = false,
                        label = { Text("Usuario", color = softWhite) },
                        textStyle = LocalTextStyle.current.copy(color = softWhite),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledContainerColor = fieldDisabledBg,
                            disabledBorderColor = softWhite,
                            disabledLabelColor = softWhite,
                            disabledTextColor = softWhite
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = {},
                        enabled = false,
                        label = { Text("Email", color = softWhite) },
                        textStyle = LocalTextStyle.current.copy(color = softWhite),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledContainerColor = fieldDisabledBg,
                            disabledBorderColor = softWhite,
                            disabledLabelColor = softWhite,
                            disabledTextColor = softWhite
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onSave,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cs.primary,
                            contentColor = softWhite
                        )
                    ) {
                        Text("CONFIRMAR CAMBIOS")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showPasswordDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cs.primary,
                            contentColor = softWhite
                        )
                    ) {
                        Text("CAMBIAR CONTRASEÑA")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F),
                            contentColor = softWhite
                        )
                    ) {
                        Text("CERRAR SESIÓN")
                    }
                }
            }
        }
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("Seleccionar imagen", color = cs.onSurface) },
            text = { Text("¿De dónde quieres obtener la imagen?", color = cs.onSurface) },
            containerColor = cs.surface,
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cs.primary,
                            contentColor = softWhite
                        )
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = softWhite)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Galería")
                    }

                    Button(
                        onClick = {
                            showImageSourceDialog = false
                            camaraPermisionLauncher.launch(Manifest.permission.CAMERA)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cs.primary,
                            contentColor = softWhite
                        )
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = softWhite)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cámara")
                    }
                }
            }
        )
    }

    if (showPasswordDialog) {
        CambiarPasswordDialog(
            onDismiss = { showPasswordDialog = false },
            onConfirm = { actual, nueva ->
                onChangePassword(actual, nueva)
                showPasswordDialog = false
            },
            softWhite = softWhite
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
fun SeccionFotoPerfil(
    fotoUri: String,
    onTakePhoto: () -> Unit,
    softWhite: Color,
    avatarBlue: Color
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
                .background(avatarBlue)
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
                    contentDescription = "Avatar",
                    tint = softWhite,
                    modifier = Modifier.size(72.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onTakePhoto,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = softWhite
            )
        ) {
            Text("Cambiar foto de perfil")
        }
    }
}

@Composable
fun CambiarPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit,
    softWhite: Color
) {
    val cs = MaterialTheme.colorScheme
    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = cs.surface,
        title = { Text("Cambiar contraseña", color = cs.onSurface) },
        text = {
            Column {
                OutlinedTextField(
                    value = actual,
                    onValueChange = { actual = it },
                    label = { Text("Contraseña actual", color = cs.onSurface) },
                    textStyle = LocalTextStyle.current.copy(color = cs.onSurface),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = cs.primary,
                        unfocusedBorderColor = cs.onSurface.copy(alpha = 0.4f),
                        cursorColor = cs.primary
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nueva,
                    onValueChange = { nueva = it },
                    label = { Text("Nueva contraseña", color = cs.onSurface) },
                    textStyle = LocalTextStyle.current.copy(color = cs.onSurface),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = cs.primary,
                        unfocusedBorderColor = cs.onSurface.copy(alpha = 0.4f),
                        cursorColor = cs.primary
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (actual.isNotBlank() && nueva.isNotBlank()) {
                        onConfirm(actual, nueva)
                    }
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.primary,
                    contentColor = softWhite
                )
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.onSurface.copy(alpha = 0.2f),
                    contentColor = cs.onSurface
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}