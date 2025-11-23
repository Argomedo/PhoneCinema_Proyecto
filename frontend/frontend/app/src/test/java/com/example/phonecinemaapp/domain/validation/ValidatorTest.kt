package com.example.phonecinemaapp.domain.validation

import org.junit.Assert.*
import org.junit.Test

class ValidatorsTest {

    // Tests para validateEmail
    @Test
    fun `validateEmail retorna null para email valido`() {
        assertNull(validateEmail("usuario@ejemplo.com"))
    }

    @Test
    fun `validateEmail retorna null para email con subdominio`() {
        assertNull(validateEmail("usuario@correo.ejemplo.com"))
    }

    @Test
    fun `validateEmail retorna null para email con guiones`() {
        assertNull(validateEmail("usuario-nombre@dominio.com"))
    }

    @Test
    fun `validateEmail retorna error para email vacio`() {
        assertEquals("El email es obligatorio", validateEmail(""))
    }

    @Test
    fun `validateEmail retorna error para email sin arroba`() {
        assertEquals("Formato de email inválido", validateEmail("usuario.dominio.com"))
    }

    @Test
    fun `validateEmail retorna error para email sin dominio`() {
        assertEquals("Formato de email inválido", validateEmail("usuario@"))
    }

    @Test
    fun `validateEmail retorna error para email con espacios`() {
        assertEquals("Formato de email inválido", validateEmail("usuario @dominio.com"))
    }

    // Tests para validateName
    @Test
    fun `validateName retorna null para nombre valido`() {
        assertNull(validateName("Juan Pérez"))
    }

    @Test
    fun `validateName retorna null para nombre con acentos`() {
        assertNull(validateName("María José Ñañez"))
    }

    @Test
    fun `validateName retorna error para nombre vacio`() {
        assertEquals("El nombre es obligatorio", validateName(""))
    }

    @Test
    fun `validateName retorna error para nombre muy corto`() {
        assertEquals("El nombre debe tener mínimo 3 letras", validateName("Al"))
    }

    @Test
    fun `validateName retorna error para nombre sin mayuscula`() {
        assertEquals("Se debe incluir una mayúscula", validateName("juan pérez"))
    }

    @Test
    fun `validateName retorna error para nombre con numeros`() {
        assertEquals("Solo letras", validateName("Juan123"))
    }

    // Tests para validatePassword
    @Test
    fun `validatePassword retorna null para password valido`() {
        assertNull(validatePassword("Pass123!"))
    }

    @Test
    fun `validatePassword retorna error para password vacio`() {
        assertEquals("La contraseña es obligatoria", validatePassword(""))
    }

    @Test
    fun `validatePassword retorna error para password muy corto`() {
        assertEquals("Mínimo 8 caracteres", validatePassword("Ab1!"))
    }

    @Test
    fun `validatePassword retorna error para password sin mayuscula`() {
        assertEquals("Debe incluir una mayúscula", validatePassword("password123!"))
    }

    @Test
    fun `validatePassword retorna error para password sin minuscula`() {
        assertEquals("Debe incluir una minúscula", validatePassword("PASSWORD123!"))
    }

    @Test
    fun `validatePassword retorna error para password sin numero`() {
        assertEquals("Debe incluir un número", validatePassword("Password!"))
    }

    @Test
    fun `validatePassword retorna error para password sin simbolo`() {
        assertEquals("Debe incluir un símbolo", validatePassword("Password123"))
    }

    @Test
    fun `validatePassword retorna error para password con espacios`() {
        assertEquals("No debe contener espacios", validatePassword("Pass 123!"))
    }

    // Tests para validatePasswordConfirm
    @Test
    fun `validatePasswordConfirm retorna null cuando las contraseñas coinciden`() {
        assertNull(validatePasswordConfirm("Pass123!", "Pass123!"))
    }

    @Test
    fun `validatePasswordConfirm retorna error cuando las contraseñas no coinciden`() {
        assertEquals("Las contraseñas no coinciden", validatePasswordConfirm("Pass123!", "Different123!"))
    }

    // Tests para validateRegisterFields
    @Test
    fun `validateRegisterFields retorna null para todos los campos validos`() {
        assertNull(validateRegisterFields(
            name = "Ana García",
            email = "ana@ejemplo.com",
            password = "Pass123!",
            confirmPassword = "Pass123!"
        ))
    }

    @Test
    fun `validateRegisterFields retorna error del nombre cuando es invalido`() {
        val error = validateRegisterFields(
            name = "", // Primer error
            email = "ana@ejemplo.com",
            password = "Pass123!",
            confirmPassword = "Pass123!"
        )
        assertEquals("El nombre es obligatorio", error)
    }

    @Test
    fun `validateRegisterFields retorna error del email cuando es invalido`() {
        val error = validateRegisterFields(
            name = "Ana García",
            email = "email-invalido", // Segundo error
            password = "Pass123!",
            confirmPassword = "Pass123!"
        )
        assertEquals("Formato de email inválido", error)
    }

    @Test
    fun `validateRegisterFields retorna error de la contraseña cuando es invalida`() {
        val error = validateRegisterFields(
            name = "Ana García",
            email = "ana@ejemplo.com",
            password = "corto", // Tercer error
            confirmPassword = "corto"
        )
        assertEquals("Mínimo 8 caracteres", error)
    }

    @Test
    fun `validateRegisterFields retorna error de confirmacion cuando no coinciden`() {
        val error = validateRegisterFields(
            name = "Ana García",
            email = "ana@ejemplo.com",
            password = "Pass123!",
            confirmPassword = "Different123!" // Cuarto error
        )
        assertEquals("Las contraseñas no coinciden", error)
    }

    @Test
    fun `validateRegisterFields valida en orden nombre, email, password, confirmacion`() {
        // Cuando múltiples campos son inválidos, debería retornar el primer error
        val error = validateRegisterFields(
            name = "", // Primer error (nombre)
            email = "invalido",
            password = "corto",
            confirmPassword = "different"
        )
        assertEquals("El nombre es obligatorio", error)
    }
}
