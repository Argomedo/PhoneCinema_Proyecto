package com.example.phonecinemaapp.ui.reseñas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.phonecinemaapp.data.local.review.ReviewEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReviewItem(
    review: ReviewEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF253B76).copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // --- Usuario y fecha ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if(!review.fotoUsuario.isNullOrEmpty()){
                    AsyncImage(
                        model = review.fotoUsuario,
                        contentDescription = "Foto de ${review.userName}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Icon(Icons.Default.Person,
                        contentDescription = "Usuario", modifier = Modifier.size(32.dp))
                }
            }

            // --- Estrellas ---
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                repeat(5) { i ->
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = if (i < review.rating.toInt()) Color(0xFFFFC107) else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // --- Comentario ---
            Spacer(modifier = Modifier.height(12.dp))
            Text(review.comment)
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}
