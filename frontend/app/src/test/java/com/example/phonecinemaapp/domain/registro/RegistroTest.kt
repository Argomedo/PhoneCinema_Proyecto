package com.example.phonecinemaapp.ui.registro

import com.example.phonecinemaapp.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegistroViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RegistroViewModel
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        // Configuramos el dispatcher principal para usar el test dispatcher
        Dispatchers.setMain(testDispatcher)

        userRepository = mockk()
        viewModel = RegistroViewModel(userRepository)
    }

    @Test
    fun `test registrarUsuario with invalid email data`() = runTest {
        // Simulamos la ejecución de la función register con un error
        coEvery {
            userRepository.register(any(), any(), any())
        } returns Result.failure(Exception("El email es obligatorio"))

        // Llenar los campos incorrectamente
        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("")  // Email vacío para simular el error
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        // Llamar al método de registro
        viewModel.registrarUsuario()

        // Verificar que el mensaje de error sea el esperado
        val uiState = viewModel.uiState.first()
        assertEquals("El email es obligatorio", uiState.errorMensaje)  // Verifica el mensaje de error correcto
        assertFalse("El registro no debe ser exitoso", uiState.registroExitoso)  // Esperamos que no haya sido exitoso
    }

    @Test
    fun `test registrarUsuario with empty password`() = runTest {
        // Simulamos la ejecución de la función register con un error
        coEvery {
            userRepository.register(any(), any(), any())
        } returns Result.failure(Exception("La contraseña es obligatoria"))

        // Llenar los campos incorrectamente
        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("juan.perez@example.com")
        viewModel.onPasswordChange("")  // Contraseña vacía para simular el error
        viewModel.onConfirmPasswordChange("")

        // Llamar al método de registro
        viewModel.registrarUsuario()

        // Verificar que el mensaje de error sea el esperado
        val uiState = viewModel.uiState.first()
        assertEquals("La contraseña es obligatoria", uiState.errorMensaje)  // Verifica el mensaje de error correcto
        assertFalse("El registro no debe ser exitoso", uiState.registroExitoso)  // Esperamos que no haya sido exitoso
    }
}
