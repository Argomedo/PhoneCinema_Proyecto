package com.example.phonecinemaapp.ui.perfil

import android.content.Context
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.data.session.UserSession
import com.example.phonecinemaapp.data.local.user.UserEntity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PerfilViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: PerfilViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var reviewRepository: ReviewRepository
    private val mockContext = mockk<Context>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mockk()
        reviewRepository = mockk()

        // Limpiar UserSession antes de cada test
        UserSession.currentUser = null

        viewModel = PerfilViewModel(userRepository, reviewRepository)
    }

    @Test
    fun perfilViewModel_estado_inicial_con_usuario_en_sesion() = runTest {
        // Configurar usuario en sesión
        val testUser = UserEntity(
            id = 1L,
            nombre = "Juan Pérez",
            email = "juan@example.com",
            fotoPerfilUrl = "https://example.com/foto.jpg",
            rol = "USER"
        )
        UserSession.currentUser = testUser

        // Crear nuevo viewModel (para que ejecute init)
        val viewModelConUsuario = PerfilViewModel(userRepository, reviewRepository)

        val uiState = viewModelConUsuario.uiState.first()
        assertEquals(1L, uiState.id)
        assertEquals("Juan Pérez", uiState.nombre)
        assertEquals("juan@example.com", uiState.email)
        assertEquals("https://example.com/foto.jpg", uiState.fotoUri)
    }

    @Test
    fun onNombreChange_actualiza_nombre() = runTest {
        viewModel.onNombreChange("Nuevo Nombre")

        val uiState = viewModel.uiState.first()
        assertEquals("Nuevo Nombre", uiState.nombre)
    }

    @Test
    fun onEmailChange_actualiza_email() = runTest {
        viewModel.onEmailChange("nuevo@email.com")

        val uiState = viewModel.uiState.first()
        assertEquals("nuevo@email.com", uiState.email)
    }

    @Test
    fun onFotoChange_actualiza_foto_y_sesion() = runTest {
        // Configurar usuario en sesión primero
        val testUser = UserEntity(
            id = 1L,
            nombre = "Test",
            email = "test@test.com",
            fotoPerfilUrl = "old.jpg",
            rol = "USER"
        )
        UserSession.currentUser = testUser

        // Crear nuevo viewModel para que capture el usuario
        val viewModelConUsuario = PerfilViewModel(userRepository, reviewRepository)

        viewModelConUsuario.onFotoChange("new.jpg")

        val uiState = viewModelConUsuario.uiState.first()
        assertEquals("new.jpg", uiState.fotoUri)

        // Verificar que UserSession se actualizó
        assertEquals("new.jpg", UserSession.currentUser?.fotoPerfilUrl)
    }

    @Test
    fun guardarCambios_error_sin_usuario() = runTest {
        // Asegurar que no hay usuario en sesión
        UserSession.currentUser = null

        val viewModelSinUsuario = PerfilViewModel(userRepository, reviewRepository)
        viewModelSinUsuario.guardarCambios(mockContext)

        val uiState = viewModelSinUsuario.uiState.first()
        assertEquals("No hay usuario cargado", uiState.errorMensaje)
    }

    @Test
    fun guardarCambios_exitoso() = runTest {
        // Configurar usuario en sesión
        val testUser = UserEntity(
            id = 1L,
            nombre = "Original",
            email = "original@test.com",
            fotoPerfilUrl = "original.jpg",
            rol = "USER"
        )
        UserSession.currentUser = testUser

        val viewModelConUsuario = PerfilViewModel(userRepository, reviewRepository)

        // Cambiar datos
        viewModelConUsuario.onNombreChange("Modificado")
        viewModelConUsuario.onEmailChange("modificado@test.com")
        viewModelConUsuario.onFotoChange("modificado.jpg")

        viewModelConUsuario.guardarCambios(mockContext)

        val uiState = viewModelConUsuario.uiState.first()
        assertEquals("Datos actualizados correctamente", uiState.successMensaje)

        // Verificar que UserSession se actualizó
        assertEquals("Modificado", UserSession.currentUser?.nombre)
        assertEquals("modificado@test.com", UserSession.currentUser?.email)
        assertEquals("modificado.jpg", UserSession.currentUser?.fotoPerfilUrl)
    }

    @Test
    fun cambiarPassword_exitoso() = runTest {
        // Configurar usuario primero
        val testUser = UserEntity(
            id = 1L,
            nombre = "Test",
            email = "test@test.com",
            fotoPerfilUrl = "",
            rol = "USER"
        )
        UserSession.currentUser = testUser

        val viewModelConUsuario = PerfilViewModel(userRepository, reviewRepository)

        coEvery {
            userRepository.cambiarPassword(1, "actual123", "nueva456")
        } returns Unit

        viewModelConUsuario.cambiarPassword("actual123", "nueva456")

        val uiState = viewModelConUsuario.uiState.first()
        assertEquals("Contraseña actualizada correctamente", uiState.successMensaje)
    }

    @Test
    fun cambiarPassword_error() = runTest {
        // Configurar usuario
        val testUser = UserEntity(
            id = 1L,
            nombre = "Test",
            email = "test@test.com",
            fotoPerfilUrl = "",
            rol = "USER"
        )
        UserSession.currentUser = testUser

        val viewModelConUsuario = PerfilViewModel(userRepository, reviewRepository)

        coEvery {
            userRepository.cambiarPassword(any(), any(), any())
        } throws Exception("Error al cambiar contraseña")

        viewModelConUsuario.cambiarPassword("actual", "nueva")

        val uiState = viewModelConUsuario.uiState.first()
        assertEquals("No se pudo cambiar la contraseña", uiState.errorMensaje)
    }

    @Test
    fun logout_actualiza_estado() = runTest {
        viewModel.logout()

        val uiState = viewModel.uiState.first()
        assertEquals(true, uiState.isLoggedOut)
    }

    @Test
    fun cargarResenasUsuario_limpia_lista() = runTest {
        // Para este test, simplemente verificamos que se llama
        // (la implementación actual solo limpia la lista)
        viewModel.cargarResenasUsuario(1L)

        val uiState = viewModel.uiState.first()
        assertEquals(emptyList<Any>(), uiState.userReviews)
    }
}