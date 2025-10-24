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

class FotoPerfil(private val context: Context){

    fun crearArchivoImagen(): File{
        val timestamp = SimpleDateFormat("ssmmHH_ddMMyyyy", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir("Fotos")
        return File.createTempFile(
            "JPEG_${timestamp}",
            ".jpeg",
            storageDir
        ).apply { //si directorio de imagen no existe, se crea aca
            parentFile?.mkdirs()
        }
    }

    fun ConsigueImagenUri(file: File): Uri{
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    fun ConsigueFotoCamara(imageUri: Uri): Intent{
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    fun ConsigueFotoGaleria(): Intent{
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image"
        }
    }
}

@Composable
fun RecuerdaFotos(): FotoPerfil{
    val context = LocalContext.current
    return remember { FotoPerfil(context) }
}
