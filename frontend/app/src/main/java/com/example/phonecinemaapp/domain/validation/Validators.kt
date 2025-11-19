package com.example.phonecinemaapp.domain.validation

import android.util.Patterns

// Valida que el email no esté vacío y cumpla patrón de email
fun validateEmail(email: String): String? {
    if (email.isBlank()) return "El email es obligatorio"
    val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return if (!ok) "Formato de email inválido" else null
}

//Ahora permite letras, números y espacios
fun validateName(name: String): String? {
    if (name.isBlank()) return "El nombre es obligatorio"
    // NUEVO regex que permite letras, números, espacios y caracteres especiales comunes
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ0-9.'-]+$")
    return if (!regex.matches(name)) "Solo letras, números y los caracteres . ' -" else null
}

// Versión opcional si necesitas validar teléfono
fun validatePhoneDigitsOnly(phone: String): String? {
    if (phone.isBlank()) return "El teléfono es obligatorio"
    if (!phone.all { it.isDigit() }) return "Solo números"
    if (phone.length !in 8..15) return "Debe tener entre 8 y 15 dígitos"
    return null
}

// Valida seguridad de la contraseña
fun validatePassword(password: String): String? {
    if (password.isBlank()) return "La contraseña es obligatoria"
    if (password.length < 8) return "Mínimo 8 caracteres"
    if (!password.any { it.isUpperCase() }) return "Debe incluir una mayúscula"
    if (!password.any { it.isLowerCase() }) return "Debe incluir una minúscula"
    if (!password.any { it.isDigit() }) return "Debe incluir un número"
    if (!password.any { !it.isLetterOrDigit() }) return "Debe incluir un símbolo"
    if (password.contains(' ')) return "No debe contener espacios"
    return null
}

// Valida que la confirmación coincida con la contraseña
fun validatePasswordConfirm(pass: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirma tu contraseña"
    return if (pass != confirm) "Las contraseñas no coinciden" else null
}

// Validacion de registro
fun validateRegisterFields(
    name: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {
    validateName(name)?.let { return it }
    validateEmail(email)?.let { return it }
    validatePassword(password)?.let { return it }
    validatePasswordConfirm(password, confirmPassword)?.let { return it }
    return null
}