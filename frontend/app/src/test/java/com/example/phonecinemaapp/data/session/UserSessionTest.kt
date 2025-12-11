package com.example.phonecinemaapp.data.session

import com.example.phonecinemaapp.data.local.user.UserEntity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserSessionTest {

    private val testUser = UserEntity(
        id = 1,
        nombre = "Juan Pérez",
        email = "juan@example.com",
        fotoPerfilUrl = "https://example.com/photo.jpg",
        rol = "ADMIN"
    )

    private val testModerator = UserEntity(
        id = 2,
        nombre = "María García",
        email = "maria@example.com",
        fotoPerfilUrl = "",
        rol = "MODERADOR"
    )

    private val testRegularUser = UserEntity(
        id = 3,
        nombre = "Carlos López",
        email = "carlos@example.com",
        fotoPerfilUrl = "",
        rol = "USUARIO"
    )

    @Before
    fun setup() {
        // Limpiar la sesión antes de cada test
        UserSession.currentUser = null
    }

    @After
    fun tearDown() {
        // Limpiar la sesión después de cada test
        UserSession.currentUser = null
    }

    @Test
    fun setUser_actualiza_currentUser_correctamente() {
        // Dado - sesión vacía
        assertNull(UserSession.currentUser)

        // Cuando
        UserSession.setUser(testUser)

        // Entonces
        assertNotNull(UserSession.currentUser)
        assertEquals(testUser.id, UserSession.currentUser?.id)
        assertEquals(testUser.nombre, UserSession.currentUser?.nombre)
        assertEquals(testUser.email, UserSession.currentUser?.email)
        assertEquals(testUser.rol, UserSession.currentUser?.rol)
    }

    @Test
    fun isAdmin_devuelve_true_cuando_usuario_es_ADMIN() {
        // Dado
        UserSession.setUser(testUser)

        // Cuando/Entonces
        assertTrue(UserSession.isAdmin())
        assertFalse(UserSession.isModerator())
        assertFalse(UserSession.isUser())
    }

    @Test
    fun isAdmin_devuelve_false_cuando_usuario_no_es_ADMIN() {
        // Dado
        UserSession.setUser(testRegularUser)

        // Cuando/Entonces
        assertFalse(UserSession.isAdmin())
        assertFalse(UserSession.isModerator())
        assertTrue(UserSession.isUser())
    }

    @Test
    fun isModerator_devuelve_true_cuando_usuario_es_MODERADOR() {
        // Dado
        UserSession.setUser(testModerator)

        // Cuando/Entonces
        assertFalse(UserSession.isAdmin())
        assertTrue(UserSession.isModerator())
        assertFalse(UserSession.isUser())
    }

    @Test
    fun isUser_devuelve_true_cuando_usuario_es_USUARIO() {
        // Dado
        UserSession.setUser(testRegularUser)

        // Cuando/Entonces
        assertFalse(UserSession.isAdmin())
        assertFalse(UserSession.isModerator())
        assertTrue(UserSession.isUser())
    }

    @Test
    fun funciones_devuelven_false_cuando_no_hay_usuario() {
        // Dado - no hay usuario establecido (ya se limpió en setup)

        // Cuando/Entonces
        assertFalse(UserSession.isAdmin())
        assertFalse(UserSession.isModerator())
        assertFalse(UserSession.isUser())
        assertNull(UserSession.currentUser)
    }

    @Test
    fun currentUser_se_puede_establecer_directamente() {
        // Dado - establecimiento directo
        UserSession.currentUser = testUser

        // Entonces
        assertNotNull(UserSession.currentUser)
        assertEquals(testUser, UserSession.currentUser)
    }

    @Test
    fun setUser_sobrescribe_usuario_existente() {
        // Dado - usuario inicial
        UserSession.setUser(testUser)
        assertEquals(testUser, UserSession.currentUser)

        // Cuando - establecer nuevo usuario
        UserSession.setUser(testModerator)

        // Entonces - debería haber cambiado
        assertEquals(testModerator, UserSession.currentUser)
        assertNotEquals(testUser, UserSession.currentUser)
    }

    @Test
    fun currentUser_puede_ser_nulo_despues_de_limpiar() {
        // Dado - usuario establecido
        UserSession.setUser(testUser)
        assertNotNull(UserSession.currentUser)

        // Cuando - limpiar
        UserSession.currentUser = null

        // Entonces
        assertNull(UserSession.currentUser)
        assertFalse(UserSession.isAdmin())
        assertFalse(UserSession.isModerator())
        assertFalse(UserSession.isUser())
    }

    @Test
    fun funciones_comparan_rol_en_mayusculas() {
        // Dado - usuario con rol en minúsculas
        val userWithLowercaseRole = testUser.copy(rol = "admin")

        // Cuando
        UserSession.setUser(userWithLowercaseRole)

        // Entonces - debería funcionar igual
        // Nota: esto depende de tu implementación real
        // Si tu comparación es case-sensitive, ajusta este test
        assertEquals("admin", UserSession.currentUser?.rol)
        assertTrue(UserSession.isAdmin())
    }

    @Test
    fun funciones_con_roles_diferentes_a_los_esperados() {
        // Dado - usuario con rol no estándar
        val userWithOtherRole = testUser.copy(rol = "INVITADO")

        // Cuando
        UserSession.setUser(userWithOtherRole)

        // Entonces
        assertFalse(UserSession.isAdmin())
        assertFalse(UserSession.isModerator())
        assertFalse(UserSession.isUser())
        assertEquals("INVITADO", UserSession.currentUser?.rol)
    }

    @Test
    fun currentUser_mantiene_todos_los_campos() {
        // Dado
        val completeUser = UserEntity(
            id = 99,
            nombre = "Usuario Completo",
            email = "completo@example.com",
            fotoPerfilUrl = "https://example.com/avatar.png",
            rol = "ADMIN"
        )

        // Cuando
        UserSession.setUser(completeUser)

        // Entonces
        val retrievedUser = UserSession.currentUser
        assertNotNull(retrievedUser)
        assertEquals(99, retrievedUser?.id)
        assertEquals("Usuario Completo", retrievedUser?.nombre)
        assertEquals("completo@example.com", retrievedUser?.email)
        assertEquals("https://example.com/avatar.png", retrievedUser?.fotoPerfilUrl)
        assertEquals("ADMIN", retrievedUser?.rol)
    }
}