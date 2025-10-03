package com.example.phonecinemaapp.ui.reseñas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.phonecinemaapp.ui.theme.PhoneCinemaYellow

@Composable
fun StarRating(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Row (modifier = modifier){
        for (i in 1..5){
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Estrella $i",
                tint = if(i <= rating) PhoneCinemaYellow else Color.White.copy(alpha = 0.5f),
                modifier = Modifier
                    .size(32.dp)
                    .clickable(enabled = enabled){ onRatingChange(i)}
                    .padding(2.dp)
            )
        }
    }
}