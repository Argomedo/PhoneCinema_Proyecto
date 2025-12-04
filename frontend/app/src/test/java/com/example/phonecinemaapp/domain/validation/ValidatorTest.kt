package com.example.phonecinemaapp.domain.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ValidatorsTest {

    // Tests para validateEmail
    @Test
    fun validateEmail_ok_email_valido() {
        val error = validateEmail("usuario@ejemplo.com")
        assertNull(error)
    }

    @Test
    fun validateEmail_ok_email_con_subdominio() {
        val error = validateEmail("usuario@correo.ejemplo.com")
        assertNull(error)
    }

    @Test
    fun validateEmail_ok_email_con_guiones() {
        val error = validateEmail("usuario-nombre@dominio.com")
        assertNull(error)
    }

    @Test
    fun validateEmail_error_email_vacio() {
        val error = validateEmail("")
        assertEquals("El email es obligatorio", error)
    }

    @Test
    fun validateEmail_error_email_sin_arroba() {
        val error = validateEmail("usuario.dominio.com")
        assertEquals("Formato de email inválido", error)
    }

    @Test
    fun validateEmail_error_email_sin_dominio() {
        val error = validateEmail("usuario@")
        assertEquals("Formato de email inválido", error)
    }

    @Test
    fun validateEmail_error_email_con_espacios() {
        val error = validateEmail("usuario @dominio.com")
        assertEquals("Formato de email inválido", error)
    }

    // Tests para validateName
    @Test
    fun validateName_ok_nombre_valido() {
        val error = validateName("Juan Pérez")
        assertNull(error)
    }

    @Test
    fun validateName_ok_nombre_con_acentos() {
        val error = validateName("María José Ñañez")
        assertNull(error)
    }

    @Test
    fun validateName_error_nombre_vacio() {
        val error = validateName("")
        assertEquals("El nombre es obligatorio", error)
    }

    @Test
    fun validateName_error_nombre_muy_corto() {
        val error = validateName("Al")
        assertEquals("El nombre debe tener mínimo 3 letras", error)
    }

    @Test
    fun validateName_error_nombre_sin_mayuscula() {
        val error = validateName("juan pérez")
        assertEquals("Se debe incluir una mayúscula", error)
    }

    @Test
    fun validateName_error_nombre_con_numeros() {
        val error = validateName("Juan123")
        assertEquals("Solo letras", error)
    }

    // Tests para validatePassword
    @Test
    fun validatePassword_ok_password_valido() {
        val error = validatePassword("Pass123!")
        assertNull(error)
    }

    @Test
    fun validatePassword_error_password_vacio() {
        val error = validatePassword("")
        assertEquals("La contraseña es obligatoria", error)
    }

    @Test
    fun validatePassword_error_password_muy_corto() {
        val error = validatePassword("Ab1!")
        assertEquals("Mínimo 8 caracteres", error)
    }

    @Test
    fun validatePassword_error_password_sin_mayuscula() {
        val error = validatePassword("password123!")
        assertEquals("Debe incluir una mayúscula", error)
    }

    @Test
    fun validatePassword_error_password_sin_minuscula() {
        val error = validatePassword("PASSWORD123!")
        assertEquals("Debe incluir una minúscula", error)
    }

    @Test
    fun validatePassword_error_password_sin_numero() {
        val error = validatePassword("Password!")
        assertEquals("Debe incluir un número", error)
    }

    @Test
    fun validatePassword_error_password_sin_simbolo() {
        val error = validatePassword("Password123")
        assertEquals("Debe incluir un símbolo", error)
    }

    @Test
    fun validatePassword_error_password_con_espacios() {
        val error = validatePassword("Pass 123!")
        assertEquals("No debe contener espacios", error)
    }

    // Tests para validatePasswordConfirm
    @Test
    fun validatePasswordConfirm_ok_contraseñas_coinciden() {
        val error = validatePasswordConfirm("Pass123!", "Pass123!")
        assertNull(error)
    }

    @Test
    fun validatePasswordConfirm_error_contraseñas_no_coinciden() {
        val error = validatePasswordConfirm("Pass123!", "Different123!")
        assertEquals("Las contraseñas no coinciden", error)
    }

    // Tests para validateRegisterFields
    @Test
    fun validateRegisterFields_ok_todos_campos_validos() {
        val error = validateRegisterFields(
            name = "Ana García",
            email = "ana@ejemplo.com",
            password = "Pass123!",
            confirmPassword = "Pass123!"
        )
        assertNull(error)
    }

    @Test
    fun validateRegisterFields_error_nombre_invalido() {
        val error = validateRegisterFields(
            name = "",
            email = "ana@ejemplo.com",
            password = "Pass123!",
            confirmPassword = "Pass123!"
        )
        assertEquals("El nombre es obligatorio", error)
    }

    @Test
    fun validateRegisterFields_error_email_invalido() {
        val error = validateRegisterFields(
            name = "Ana García",
            email = "email-invalido",
            password = "Pass123!",
            confirmPassword = "Pass123!"
        )
        assertEquals("Formato de email inválido", error)
    }

    @Test
    fun validateRegisterFields_error_password_invalida() {
        val error = validateRegisterFields(
            name = "Ana García",
            email = "ana@ejemplo.com",
            password = "corto",
            confirmPassword = "corto"
        )
        assertEquals("Mínimo 8 caracteres", error)
    }

    @Test
    fun validateRegisterFields_error_confirmacion_no_coincide() {
        val error = validateRegisterFields(
            name = "Ana García",
            email = "ana@ejemplo.com",
            password = "Pass123!",
            confirmPassword = "Different123!"
        )
        assertEquals("Las contraseñas no coinciden", error)
    }

    @Test
    fun validateRegisterFields_error_validacion_orden_correcto() {
        val error = validateRegisterFields(
            name = "",
            email = "invalido",
            password = "corto",
            confirmPassword = "different"
        )
        assertEquals("El nombre es obligatorio", error)
    }
}