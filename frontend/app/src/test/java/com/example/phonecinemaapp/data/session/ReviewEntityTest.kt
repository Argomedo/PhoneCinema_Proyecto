package com.example.phonecinemaapp.data.local.review

import com.example.phonecinemaapp.data.local.review.ReviewEntity.ReviewEntity
import org.junit.Assert.*
import org.junit.Test

class ReviewEntityTest {

    @Test
    fun reviewEntity_creacion_con_valores_minimos() {
        // Cuando - crear una reseña con valores mínimos
        val review = ReviewEntity(
            id = 1L,
            movieId = 100,
            userId = 50L,
            userName = "Juan Pérez",
            fotoUsuario = "",
            rating = 4,
            comment = "Buena película",
            timestamp = 1234567890L
        )

        // Entonces - todos los valores deben ser los establecidos
        assertEquals(1L, review.id)
        assertEquals(100, review.movieId)
        assertEquals(50L, review.userId)
        assertEquals("Juan Pérez", review.userName)
        assertEquals("", review.fotoUsuario)
        assertEquals(4, review.rating)
        assertEquals("Buena película", review.comment)
        assertEquals(1234567890L, review.timestamp)
    }

    @Test
    fun reviewEntity_creacion_con_foto_usuario() {
        // Cuando - crear una reseña con foto de usuario
        val review = ReviewEntity(
            id = 2L,
            movieId = 101,
            userId = 51L,
            userName = "María García",
            fotoUsuario = "https://example.com/photo.jpg",
            rating = 5,
            comment = "Excelente!",
            timestamp = 1234567891L
        )

        // Entonces
        assertEquals("https://example.com/photo.jpg", review.fotoUsuario)
        assertEquals(5, review.rating)
        assertEquals("Excelente!", review.comment)
    }

    @Test
    fun reviewEntity_timestamp_por_defecto() {
        // Cuando - crear una reseña sin especificar timestamp
        val review = ReviewEntity(
            id = 3L,
            movieId = 102,
            userId = 52L,
            userName = "Carlos López",
            fotoUsuario = "",
            rating = 3,
            comment = "Regular"
            // timestamp usará el valor por defecto
        )

        // Entonces - timestamp debe ser mayor a 0 (tiempo actual del sistema)
        assertTrue(review.timestamp > 0L)
    }

    @Test
    fun reviewEntity_fotoUsuario_por_defecto() {
        // Cuando - crear una reseña sin especificar fotoUsuario
        val review = ReviewEntity(
            id = 4L,
            movieId = 103,
            userId = 53L,
            userName = "Ana Martínez",
            rating = 2,
            comment = "No me gustó"
            // fotoUsuario usará el valor por defecto ""
        )

        // Entonces - fotoUsuario debe estar vacío por defecto
        assertEquals("", review.fotoUsuario)
    }

    @Test
    fun reviewEntity_equals_mismos_valores() {
        // Dado - dos reseñas con los mismos valores
        val review1 = ReviewEntity(
            id = 10L,
            movieId = 200,
            userId = 100L,
            userName = "Usuario",
            rating = 4,
            comment = "Iguales",
            timestamp = 1000L
        )

        val review2 = ReviewEntity(
            id = 10L,
            movieId = 200,
            userId = 100L,
            userName = "Usuario",
            rating = 4,
            comment = "Iguales",
            timestamp = 1000L
        )

        // Entonces - deben ser iguales
        assertEquals(review1, review2)
        assertEquals(review1.hashCode(), review2.hashCode())
    }

    @Test
    fun reviewEntity_equals_diferentes_ids() {
        // Dado - dos reseñas con diferentes IDs
        val review1 = ReviewEntity(
            id = 1L,
            movieId = 200,
            userId = 100L,
            userName = "Usuario",
            rating = 4,
            comment = "Comentario",
            timestamp = 1000L
        )

        val review2 = ReviewEntity(
            id = 2L, // Diferente ID
            movieId = 200,
            userId = 100L,
            userName = "Usuario",
            rating = 4,
            comment = "Comentario",
            timestamp = 1000L
        )

        // Entonces - NO deben ser iguales
        assertNotEquals(review1, review2)
    }

    @Test
    fun reviewEntity_equals_diferentes_movieIds() {
        // Dado - dos reseñas con diferentes movieIds
        val review1 = ReviewEntity(
            id = 1L,
            movieId = 100,
            userId = 100L,
            userName = "Usuario",
            rating = 4,
            comment = "Comentario",
            timestamp = 1000L
        )

        val review2 = ReviewEntity(
            id = 1L,
            movieId = 101, // Diferente movieId
            userId = 100L,
            userName = "Usuario",
            rating = 4,
            comment = "Comentario",
            timestamp = 1000L
        )

        // Entonces - NO deben ser iguales
        assertNotEquals(review1, review2)
    }

    @Test
    fun reviewEntity_toString_contiene_informacion_relevante() {
        // Dado
        val review = ReviewEntity(
            id = 5L,
            movieId = 300,
            userId = 200L,
            userName = "Test User",
            rating = 5,
            comment = "Muy buena",
            timestamp = 1234567890L
        )

        // Cuando
        val toStringResult = review.toString()

        // Entonces - debe contener información clave
        assertTrue(toStringResult.contains("id=5"))
        assertTrue(toStringResult.contains("movieId=300"))
        assertTrue(toStringResult.contains("userId=200"))
        assertTrue(toStringResult.contains("userName=Test User"))
        assertTrue(toStringResult.contains("rating=5"))
        assertTrue(toStringResult.contains("comment=Muy buena"))
    }

    @Test
    fun reviewEntity_copy_funciona_correctamente() {
        // Dado - una reseña original
        val original = ReviewEntity(
            id = 6L,
            movieId = 400,
            userId = 300L,
            userName = "Original",
            fotoUsuario = "original.jpg",
            rating = 3,
            comment = "Original",
            timestamp = 1000L
        )

        // Cuando - crear una copia cambiando algunos valores
        val copy = original.copy(
            rating = 5,
            comment = "Modificado"
        )

        // Entonces - algunos valores cambian, otros se mantienen
        assertEquals(original.id, copy.id)
        assertEquals(original.movieId, copy.movieId)
        assertEquals(original.userId, copy.userId)
        assertEquals(original.userName, copy.userName)
        assertEquals(original.fotoUsuario, copy.fotoUsuario)
        assertEquals(original.timestamp, copy.timestamp)

        // Estos deben ser diferentes
        assertEquals(5, copy.rating) // Cambiado
        assertEquals("Modificado", copy.comment) // Cambiado
    }

    @Test
    fun reviewEntity_rating_dentro_de_rango_valido() {
        // Dado - ratings válidos
        val review1 = ReviewEntity(
            id = 7L,
            movieId = 500,
            userId = 400L,
            userName = "Usuario",
            rating = 1, // Mínimo
            comment = "Mínimo"
        )

        val review2 = ReviewEntity(
            id = 8L,
            movieId = 500,
            userId = 400L,
            userName = "Usuario",
            rating = 5, // Máximo
            comment = "Máximo"
        )

        // Entonces - ratings deben ser aceptados
        assertEquals(1, review1.rating)
        assertEquals(5, review2.rating)
    }
}

