package com.example.phonecinemaapp.utils

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FotoPerfil(private val context: Context){

    fun crearImagen(): File{
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
}
//Esta wea esta super early lo veo el jueves si lees esto mañana
