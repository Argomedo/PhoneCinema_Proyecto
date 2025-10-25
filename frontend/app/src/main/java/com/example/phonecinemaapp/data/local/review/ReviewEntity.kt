package com.example.phonecinemaapp.data.local.review

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.phonecinemaapp.data.local.user.UserEntity

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val movieId: Int,
    val userId: Long,
    val userName: String,        // Faltaba actualizar este campo
    val fotoUsuario: String = "", //capo nuevo para las fotos
    val rating: Float,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
)
