package com.example.phonecinemaapp.ui.login

import com.example.phonecinemaapp.data.repository.AuthRepository
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
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        userRepository = mockk()
        viewModel = LoginViewModel(authRepository, userRepository)
    }

    @Test
    fun iniciarSesion_error_campos_vacios() = runTest {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")
        viewModel.iniciarSesion()

        val uiState = viewModel.uiState.first()
        assertEquals("Debes completar todos los campos", uiState.errorMsg)
        assertFalse(uiState.loginExitoso)
    }

    @Test
    fun iniciarSesion_error_email_vacio() = runTest {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("password123")
        viewModel.iniciarSesion()

        val uiState = viewModel.uiState.first()
        assertEquals("Debes completar todos los campos", uiState.errorMsg)
        assertFalse(uiState.loginExitoso)
    }

    @Test
    fun iniciarSesion_error_password_vacia() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("")
        viewModel.iniciarSesion()

        val uiState = viewModel.uiState.first()
        assertEquals("Debes completar todos los campos", uiState.errorMsg)
        assertFalse(uiState.loginExitoso)
    }


    @Test
    fun iniciarSesion_error_credenciales_incorrectas() = runTest {
        coEvery {
            authRepository.login(any(), any())
        } throws Exception("Credenciales incorrectas")

        viewModel.onEmailChange("wrong@example.com")
        viewModel.onPasswordChange("wrongpassword")
        viewModel.iniciarSesion()

        val uiState = viewModel.uiState.first()
        assertEquals("Credenciales incorrectas", uiState.errorMsg)
        assertFalse(uiState.loginExitoso)
    }
}