package com.example.phonecinemaapp.ui.registro

import com.example.phonecinemaapp.data.repository.UserRepository
import io.mockk.coEvery
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
class RegistroViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RegistroViewModel
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mockk()
        viewModel = RegistroViewModel(userRepository)
    }

    @Test
    fun registrarUsuario_error_email_invalido() = runTest {
        coEvery {
            userRepository.register(any(), any(), any())
        } returns Result.failure(Exception("El email es obligatorio"))

        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        viewModel.registrarUsuario()

        val uiState = viewModel.uiState.first()
        assertEquals("El email es obligatorio", uiState.errorMensaje)
        assertFalse(uiState.registroExitoso)
    }

    @Test
    fun registrarUsuario_error_password_vacia() = runTest {
        coEvery {
            userRepository.register(any(), any(), any())
        } returns Result.failure(Exception("La contraseña es obligatoria"))

        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("juan.perez@example.com")
        viewModel.onPasswordChange("")
        viewModel.onConfirmPasswordChange("")

        viewModel.registrarUsuario()

        val uiState = viewModel.uiState.first()
        assertEquals("La contraseña es obligatoria", uiState.errorMensaje)
        assertFalse(uiState.registroExitoso)
    }
}