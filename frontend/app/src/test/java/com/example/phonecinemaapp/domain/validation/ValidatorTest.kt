package com.example.phonecinemaapp.domain.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ValidatorsTest {

    // ================================
    // PRUEBAS PARA validateEmail
    // ================================

    @Test
    fun validateEmail_valido_retorna_null() {
        assertNull(validateEmail("usuario@example.com"))
        assertNull(validateEmail("usuario.nombre@sub.dominio.com"))
        assertNull(validateEmail("usuario+tag@example.com"))
        assertNull(validateEmail("a@b.co"))
    }

    @Test
    fun validateEmail_vacio_retorna_error() {
        assertEquals("El email es obligatorio", validateEmail(""))
        assertEquals("El email es obligatorio", validateEmail("   "))
    }

    @Test
    fun validateEmail_demasiado_largo_retorna_error() {
        val longEmail = "a".repeat(250) + "@example.com"
        assertEquals("El email es demasiado largo", validateEmail(longEmail))
    }

    @Test
    fun validateEmail_con_espacios_retorna_error() {
        assertEquals("El email no debe contener espacios", validateEmail("usuario @example.com"))
        assertEquals("El email no debe contener espacios", validateEmail("usuario@ example.com"))
    }

    @Test
    fun validateEmail_sin_arroba_retorna_error() {
        assertEquals("Formato de email inválido", validateEmail("usuarioexample.com"))
    }

    @Test
    fun validateEmail_con_multiples_arroba_retorna_error() {
        assertEquals("Formato de email inválido", validateEmail("usuario@@example.com"))
    }

    @Test
    fun validateEmail_local_part_vacio_retorna_error() {
        assertEquals("Formato de email inválido", validateEmail("@example.com"))
    }

    @Test
    fun validateEmail_local_part_demasiado_largo_retorna_error() {
        val longLocal = "a".repeat(65) + "@example.com"
        assertEquals("Formato de email inválido", validateEmail(longLocal))
    }

    @Test
    fun validateEmail_dominio_invalido_retorna_error() {
        assertEquals("Formato de email inválido", validateEmail("usuario@.com"))
        assertEquals("Formato de email inválido", validateEmail("usuario@example."))
        assertEquals("Formato de email inválido", validateEmail("usuario@example..com"))
    }

    @Test
    fun validateEmail_caracteres_invalidos_en_local_retorna_error() {
        assertEquals("Formato de email inválido", validateEmail("user@name@example.com"))
        assertEquals("Formato de email inválido", validateEmail("user#name@example.com"))
        assertEquals("Formato de email inválido", validateEmail("user&name@example.com"))
    }

    @Test
    fun validateEmail_tld_invalido_retorna_error() {
        assertEquals("Formato de email inválido", validateEmail("usuario@example.c"))
        assertEquals("Formato de email inválido", validateEmail("usuario@example.123"))
        assertEquals("Formato de email inválido", validateEmail("usuario@example.abcdefghijklmnopqrstuvwxyz"))
    }

    // ================================
    // PRUEBAS PARA validateName
    // ================================

    @Test
    fun validateName_valido_retorna_null() {
        assertNull(validateName("juan"))
        assertNull(validateName("juan_perez"))
        assertNull(validateName("juan-perez"))
        assertNull(validateName("juan.perez"))
        assertNull(validateName("juan123"))
        assertNull(validateName("JuAnPeReZ"))
    }

    @Test
    fun validateName_vacio_retorna_error() {
        assertEquals("El nombre de usuario es obligatorio", validateName(""))
        assertEquals("El nombre de usuario es obligatorio", validateName("   "))
    }

    @Test
    fun validateName_muy_corto_retorna_error() {
        assertEquals("El nombre de usuario debe tener al menos 3 caracteres", validateName("ab"))
        assertEquals("El nombre de usuario debe tener al menos 3 caracteres", validateName("a"))
    }

    @Test
    fun validateName_muy_largo_retorna_error() {
        val longName = "a".repeat(31)
        assertEquals("El nombre de usuario no puede superar los 30 caracteres", validateName(longName))
    }

    @Test
    fun validateName_con_espacios_retorna_error() {
        assertEquals("El nombre de usuario no debe contener espacios", validateName("juan perez"))
        assertEquals("El nombre de usuario no debe contener espacios", validateName(" juan"))
        assertEquals("El nombre de usuario no debe contener espacios", validateName("juan "))
    }

    @Test
    fun validateName_caracteres_invalidos_retorna_error() {
        assertEquals(
            "El nombre de usuario solo puede contener letras, números, punto, guion y guion bajo",
            validateName("juan@perez")
        )
        assertEquals(
            "El nombre de usuario solo puede contener letras, números, punto, guion y guion bajo",
            validateName("juan#perez")
        )
        assertEquals(
            "El nombre de usuario solo puede contener letras, números, punto, guion y guion bajo",
            validateName("juan!perez")
        )
    }

    @Test
    fun validateName_comienza_con_caracter_invalido_retorna_error() {
        assertEquals("El nombre de usuario es inválido", validateName(".juan"))
        assertEquals("El nombre de usuario es inválido", validateName("_juan"))
        assertEquals("El nombre de usuario es inválido", validateName("-juan"))
    }

    @Test
    fun validateName_contiene_dobles_caracteres_retorna_error() {
        assertEquals("El nombre de usuario es inválido", validateName("juan..perez"))
        assertEquals("El nombre de usuario es inválido", validateName("juan__perez"))
        assertEquals("El nombre de usuario es inválido", validateName("juan--perez"))
    }

    // ================================
    // PRUEBAS PARA validatePassword
    // ================================

    @Test
    fun validatePassword_valido_retorna_null() {
        assertNull(validatePassword("Password123!"))
        assertNull(validatePassword("MiClave123#"))
        assertNull(validatePassword("Test@2024"))
    }

    @Test
    fun validatePassword_vacio_retorna_error() {
        assertEquals("La contraseña es obligatoria", validatePassword(""))
        assertEquals("La contraseña es obligatoria", validatePassword("   "))
    }

    @Test
    fun validatePassword_muy_corta_retorna_error() {
        assertEquals("La contraseña debe tener mínimo 8 caracteres", validatePassword("Abc123!"))
        assertEquals("La contraseña debe tener mínimo 8 caracteres", validatePassword("A1!"))
    }

    @Test
    fun validatePassword_muy_larga_retorna_error() {
        val longPassword = "A".repeat(70) + "a1!"
        assertEquals("La contraseña no puede superar los 72 caracteres", validatePassword(longPassword))
    }

    @Test
    fun validatePassword_con_espacios_retorna_error() {
        assertEquals("La contraseña no debe contener espacios", validatePassword("Password 123!"))
        assertEquals("La contraseña no debe contener espacios", validatePassword(" Password123!"))
        assertEquals("La contraseña no debe contener espacios", validatePassword("Password123! "))
    }

    @Test
    fun validatePassword_sin_mayuscula_retorna_error() {
        assertEquals("La contraseña debe incluir una mayúscula", validatePassword("password123!"))
    }

    @Test
    fun validatePassword_sin_minuscula_retorna_error() {
        assertEquals("La contraseña debe incluir una minúscula", validatePassword("PASSWORD123!"))
    }

    @Test
    fun validatePassword_sin_numero_retorna_error() {
        assertEquals("La contraseña debe incluir un número", validatePassword("Password!!"))
    }

    @Test
    fun validatePassword_sin_simbolo_retorna_error() {
        assertEquals("La contraseña debe incluir un símbolo", validatePassword("Password123"))
    }

    // ================================
    // PRUEBAS PARA validatePasswordConfirm
    // ================================

    @Test
    fun validatePasswordConfirm_valido_retorna_null() {
        assertNull(validatePasswordConfirm("Password123!", "Password123!"))
    }

    @Test
    fun validatePasswordConfirm_vacio_retorna_error() {
        assertEquals("Confirma tu contraseña", validatePasswordConfirm("Password123!", ""))
        assertEquals("Confirma tu contraseña", validatePasswordConfirm("Password123!", "   "))
    }

    @Test
    fun validatePasswordConfirm_no_coincide_retorna_error() {
        assertEquals("Las contraseñas no coinciden", validatePasswordConfirm("Password123!", "Password123"))
        assertEquals("Las contraseñas no coinciden", validatePasswordConfirm("Password123!", "password123!"))
    }

    // ================================
    // PRUEBAS PARA validateRegisterFields
    // ================================

    @Test
    fun validateRegisterFields_todo_valido_retorna_null() {
        assertNull(validateRegisterFields(
            username = "juan_perez",
            email = "juan@example.com",
            password = "Password123!",
            confirmPassword = "Password123!"
        ))
    }

    @Test
    fun validateRegisterFields_nombre_invalido_retorna_error_nombre() {
        assertEquals("El nombre de usuario es obligatorio", validateRegisterFields(
            username = "",
            email = "juan@example.com",
            password = "Password123!",
            confirmPassword = "Password123!"
        ))
    }

    @Test
    fun validateRegisterFields_email_invalido_retorna_error_email() {
        assertEquals("Formato de email inválido", validateRegisterFields(
            username = "juan_perez",
            email = "juan@",
            password = "Password123!",
            confirmPassword = "Password123!"
        ))
    }

    @Test
    fun validateRegisterFields_password_invalido_retorna_error_password() {
        assertEquals("La contraseña debe incluir un símbolo", validateRegisterFields(
            username = "juan_perez",
            email = "juan@example.com",
            password = "Password123",
            confirmPassword = "Password123"
        ))
    }

    @Test
    fun validateRegisterFields_confirmacion_invalida_retorna_error_confirmacion() {
        assertEquals("Las contraseñas no coinciden", validateRegisterFields(
            username = "juan_perez",
            email = "juan@example.com",
            password = "Password123!",
            confirmPassword = "Password123"
        ))
    }

    @Test
    fun validateRegisterFields_multiples_errores_retorna_primer_error() {
        // Debería retornar el primer error encontrado (nombre)
        assertEquals("El nombre de usuario es obligatorio", validateRegisterFields(
            username = "",
            email = "invalido",
            password = "corta",
            confirmPassword = "diferente"
        ))
    }

    @Test
    fun validateRegisterFields_orden_validacion() {
        // Primero valida nombre, luego email, luego password, luego confirmación
        // Test con nombre válido pero email inválido
        assertEquals("Formato de email inválido", validateRegisterFields(
            username = "juan_perez", // válido
            email = "invalido", // inválido
            password = "Password123!", // válido
            confirmPassword = "Password123!" // válido
        ))
    }
}