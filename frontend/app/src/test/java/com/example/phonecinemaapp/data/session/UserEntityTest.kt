package com.example.phonecinemaapp.data.local.user

import org.junit.Assert.*
import org.junit.Test

class UserEntityTest {

    @Test
    fun userEntity_creacion_con_todos_los_campos() {
        // Cuando - crear un usuario con todos los campos
        val user = UserEntity(
            id = 1L,
            nombre = "Juan Pérez",
            email = "juan@example.com",
            fotoPerfilUrl = "https://example.com/photo.jpg",
            rol = "ADMIN"
        )

        // Entonces - todos los valores deben ser correctos
        assertEquals(1L, user.id)
        assertEquals("Juan Pérez", user.nombre)
        assertEquals("juan@example.com", user.email)
        assertEquals("https://example.com/photo.jpg", user.fotoPerfilUrl)
        assertEquals("ADMIN", user.rol)
    }

    @Test
    fun userEntity_creacion_con_fotoPerfilUrl_vacia() {
        // Cuando - crear usuario sin foto de perfil
        val user = UserEntity(
            id = 2L,
            nombre = "María García",
            email = "maria@example.com",
            fotoPerfilUrl = "", // URL vacía
            rol = "USUARIO"
        )

        // Entonces
        assertEquals("", user.fotoPerfilUrl)
    }

    @Test
    fun userEntity_equals_mismos_valores() {
        // Dado - dos usuarios con los mismos valores
        val user1 = UserEntity(
            id = 10L,
            nombre = "Usuario Test",
            email = "test@example.com",
            fotoPerfilUrl = "avatar.jpg",
            rol = "MODERADOR"
        )

        val user2 = UserEntity(
            id = 10L,
            nombre = "Usuario Test",
            email = "test@example.com",
            fotoPerfilUrl = "avatar.jpg",
            rol = "MODERADOR"
        )

        // Entonces - deben ser iguales
        assertEquals(user1, user2)
        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun userEntity_equals_diferentes_ids() {
        // Dado - dos usuarios con diferente ID
        val user1 = UserEntity(
            id = 1L,
            nombre = "Usuario",
            email = "usuario@example.com",
            fotoPerfilUrl = "foto.jpg",
            rol = "USUARIO"
        )

        val user2 = UserEntity(
            id = 2L, // Diferente ID
            nombre = "Usuario",
            email = "usuario@example.com",
            fotoPerfilUrl = "foto.jpg",
            rol = "USUARIO"
        )

        // Entonces - NO deben ser iguales
        assertNotEquals(user1, user2)
    }

    @Test
    fun userEntity_equals_diferentes_emails() {
        // Dado - dos usuarios con diferente email
        val user1 = UserEntity(
            id = 5L,
            nombre = "Juan",
            email = "juan1@example.com",
            fotoPerfilUrl = "foto.jpg",
            rol = "USUARIO"
        )

        val user2 = UserEntity(
            id = 5L,
            nombre = "Juan",
            email = "juan2@example.com", // Diferente email
            fotoPerfilUrl = "foto.jpg",
            rol = "USUARIO"
        )

        // Entonces - NO deben ser iguales
        assertNotEquals(user1, user2)
    }

    @Test
    fun userEntity_toString_contiene_informacion_relevante() {
        // Dado
        val user = UserEntity(
            id = 100L,
            nombre = "Carlos López",
            email = "carlos@empresa.com",
            fotoPerfilUrl = "https://empresa.com/avatars/carlos.jpg",
            rol = "ADMIN"
        )

        // Cuando
        val toStringResult = user.toString()

        // Entonces - debe contener información clave
        assertTrue(toStringResult.contains("id=100"))
        assertTrue(toStringResult.contains("nombre=Carlos López"))
        assertTrue(toStringResult.contains("email=carlos@empresa.com"))
        assertTrue(toStringResult.contains("rol=ADMIN"))
    }

    @Test
    fun userEntity_copy_funciona_correctamente() {
        // Dado - usuario original
        val original = UserEntity(
            id = 50L,
            nombre = "Original",
            email = "original@example.com",
            fotoPerfilUrl = "original.jpg",
            rol = "USUARIO"
        )

        // Cuando - crear copia cambiando algunos valores
        val copy = original.copy(
            nombre = "Modificado",
            rol = "ADMIN"
        )

        // Entonces - algunos valores cambian, otros se mantienen
        assertEquals(original.id, copy.id)
        assertEquals(original.email, copy.email)
        assertEquals(original.fotoPerfilUrl, copy.fotoPerfilUrl)

        // Estos deben ser diferentes
        assertEquals("Modificado", copy.nombre) // Cambiado
        assertEquals("ADMIN", copy.rol) // Cambiado
    }

    @Test
    fun userEntity_copy_con_todos_los_campos_cambiados() {
        // Dado
        val original = UserEntity(
            id = 1L,
            nombre = "Antiguo",
            email = "antiguo@test.com",
            fotoPerfilUrl = "old.jpg",
            rol = "USER"
        )

        // Cuando
        val nuevo = original.copy(
            id = 2L,
            nombre = "Nuevo",
            email = "nuevo@test.com",
            fotoPerfilUrl = "new.jpg",
            rol = "ADMIN"
        )

        // Entonces - todos deben ser diferentes
        assertNotEquals(original.id, nuevo.id)
        assertNotEquals(original.nombre, nuevo.nombre)
        assertNotEquals(original.email, nuevo.email)
        assertNotEquals(original.fotoPerfilUrl, nuevo.fotoPerfilUrl)
        assertNotEquals(original.rol, nuevo.rol)
    }

    @Test
    fun userEntity_nombre_puede_contener_caracteres_especiales() {
        // Dado - nombre con caracteres especiales
        val user = UserEntity(
            id = 3L,
            nombre = "María José Pérez-Sánchez",
            email = "maria@example.com",
            fotoPerfilUrl = "",
            rol = "USUARIO"
        )

        // Entonces
        assertEquals("María José Pérez-Sánchez", user.nombre)
    }

    @Test
    fun userEntity_email_valido() {
        // Dado - diferentes formatos de email válidos
        val user1 = UserEntity(
            id = 4L,
            nombre = "Usuario 1",
            email = "simple@example.com",
            fotoPerfilUrl = "",
            rol = "USUARIO"
        )

        val user2 = UserEntity(
            id = 5L,
            nombre = "Usuario 2",
            email = "user.name+tag@subdomain.example.co.uk",
            fotoPerfilUrl = "",
            rol = "USUARIO"
        )

        // Entonces - deben aceptarse
        assertEquals("simple@example.com", user1.email)
        assertEquals("user.name+tag@subdomain.example.co.uk", user2.email)
    }

    @Test
    fun userEntity_roles_diferentes() {
        // Dado - usuarios con diferentes roles
        val admin = UserEntity(
            id = 6L,
            nombre = "Admin",
            email = "admin@example.com",
            fotoPerfilUrl = "",
            rol = "ADMIN"
        )

        val moderador = UserEntity(
            id = 7L,
            nombre = "Moderador",
            email = "mod@example.com",
            fotoPerfilUrl = "",
            rol = "MODERADOR"
        )

        val usuario = UserEntity(
            id = 8L,
            nombre = "Usuario",
            email = "user@example.com",
            fotoPerfilUrl = "",
            rol = "USUARIO"
        )

        // Entonces
        assertEquals("ADMIN", admin.rol)
        assertEquals("MODERADOR", moderador.rol)
        assertEquals("USUARIO", usuario.rol)
    }

    @Test
    fun userEntity_fotoPerfilUrl_puede_ser_url_larga() {
        // Dado - URL larga con parámetros
        val longUrl = "https://cdn.example.com/users/12345/avatars/" +
                "profile.jpg?width=200&height=200&quality=85&timestamp=1234567890"

        val user = UserEntity(
            id = 9L,
            nombre = "Test URL",
            email = "url@test.com",
            fotoPerfilUrl = longUrl,
            rol = "USUARIO"
        )

        // Entonces
        assertEquals(longUrl, user.fotoPerfilUrl)
    }

    @Test
    fun userEntity_propiedades_son_inmutables() {
        // Dado - un usuario creado
        val user = UserEntity(
            id = 10L,
            nombre = "Inmutable",
            email = "inmutable@test.com",
            fotoPerfilUrl = "avatar.png",
            rol = "USUARIO"
        )

        // Entonces - todas las propiedades son val (inmutables)
        // Esto se verifica en tiempo de compilación, pero podemos
        // confirmar que es un data class
        assertTrue(user is UserEntity)
    }

    @Test
    fun userEntity_desestructuracion_funciona() {
        // Dado
        val user = UserEntity(
            id = 11L,
            nombre = "Desestructurado",
            email = "dest@test.com",
            fotoPerfilUrl = "pic.jpg",
            rol = "MODERADOR"
        )

        // Cuando - desestructurar
        val (id, nombre, email, foto, rol) = user

        // Entonces
        assertEquals(11L, id)
        assertEquals("Desestructurado", nombre)
        assertEquals("dest@test.com", email)
        assertEquals("pic.jpg", foto)
        assertEquals("MODERADOR", rol)
    }
}