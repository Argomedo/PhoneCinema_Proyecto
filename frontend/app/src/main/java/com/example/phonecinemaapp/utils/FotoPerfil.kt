package com.example.phonecinemaapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FotoPerfil(private val context: Context) {

    // Crear archivo temporal para la imagen
    fun crearArchivoImagen(): File {
        val timestamp = SimpleDateFormat("ssmmHH_ddMMyyyy", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir("Fotos")
        return File.createTempFile(
            "JPEG_${timestamp}",
            ".jpeg",
            storageDir
        ).apply {
            // Si el directorio de imágenes no existe, se crea aquí
            parentFile?.mkdirs()
        }
    }

    // NUEVO: Obtener URI segura para el archivo usando FileProvider
    fun ConsigueImagenUri(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    // NUEVO: Intent para abrir la cámara
    fun ConsigueFotoCamara(imageUri: Uri): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    // NUEVO: Intent para abrir la galería
    fun ConsigueFotoGaleria(): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image"
        }
    }
}

// NUEVO: Función de composición para recordar la instancia de FotoPerfil
@Composable
fun RecuerdaFotos(): FotoPerfil {
    val context = LocalContext.current
    return remember { FotoPerfil(context) }
}