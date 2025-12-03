package com.example.phonecinemaapp.ui.feedback

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.phonecinema.data.dto.FeedbackDto

@Composable
fun FeedbackScreen(viewModel: FeedbackViewModel, navController: NavController) {
    val context = LocalContext.current
    val feedbackState = remember { mutableStateOf(TextFieldValue("")) }
    val user = viewModel.getUser() // Obtener el usuario logueado
    val feedbackSent = remember { mutableStateOf(false) }  // Estado para saber si el feedback se envió correctamente

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título con estilo
        Text(
            text = "Déjanos tu feedback",
            style = MaterialTheme.typography.titleLarge, // Estilo más grande para destacar
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp), // Espaciado adecuado
            color = MaterialTheme.colorScheme.primary
        )

        // Mostrar el nombre del usuario y su foto de perfil
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Usuario",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Comentario de: ${user?.nombre ?: "Usuario Desconocido"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el feedback con más detalles
        TextField(
            value = feedbackState.value,
            onValueChange = { feedbackState.value = it },
            label = { Text("Escribe tu feedback") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp),
            maxLines = 5,
            placeholder = { Text("Cuéntanos qué te parece nuestra app...") }, // Texto sugerido
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para enviar el feedback con estilo
        Button(
            onClick = {
                // Si el comentario no está vacío
                if (feedbackState.value.text.isNotBlank()) {
                    // Creamos el DTO de Feedback con los datos del usuario
                    val feedback = FeedbackDto(
                        usuarioId = user?.id ?: 0L,
                        userName = user?.nombre ?: "Usuario Desconocido",
                        fotoUsuario = user?.fotoPerfilUrl ?: "",
                        mensaje = feedbackState.value.text
                    )
                    // Enviamos el feedback utilizando el ViewModel
                    viewModel.sendFeedback(feedback)
                    feedbackSent.value = true // Marcamos que el feedback fue enviado

                    // Mostramos el Toast de agradecimiento
                    Toast.makeText(context, "¡Gracias por tu feedback!", Toast.LENGTH_SHORT).show()

                    navController.popBackStack() // Regresar a la pantalla anterior
                } else {
                    Toast.makeText(context, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Enviar Feedback", color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Mensaje de confirmación si el feedback fue enviado
        if (feedbackSent.value) {
            Text(
                text = "¡Gracias por tu feedback!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
