package com.example.phonecinemaapp.domain.validation

import android.util.Patterns

// Valida el nombre (mínimo 3 caracteres, solo letras y espacios)
fun validateName(name: String): String? {
    if (name.isBlank()) return "El nombre es obligatorio"
    if (name.length < 3) return "El nombre debe tener al menos 3 caracteres"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if (!regex.matches(name)) "Solo se permiten letras y espacios" else null
}

// Valida formato de email
fun validateEmail(email: String): String? {
    if (email.isBlank()) return "El correo es obligatorio"
    val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return if (!ok) "Ingrese un correo válido" else null
}

// Valida seguridad de la contraseña
fun validatePassword(password: String): String? {
    if (password.isBlank()) return "La contraseña es obligatoria"
    if (password.length < 6) return "La contraseña debe tener mínimo 6 caracteres"
    if (!password.any { it.isUpperCase() }) return "Debe incluir una mayúscula"
    if (!password.any { it.isLowerCase() }) return "Debe incluir una minúscula"
    if (!password.any { it.isDigit() }) return "Debe incluir un número"
    if (!password.any { !it.isLetterOrDigit() }) return "Debe incluir un símbolo"
    if (password.contains(' ')) return "No debe contener espacios"
    return null
}

// Valida coincidencia de contraseñas
fun validatePasswordConfirm(pass: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirma tu contraseña"
    return if (pass != confirm) "Las contraseñas no coinciden" else null
}

// Valida todo el formulario completo
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
