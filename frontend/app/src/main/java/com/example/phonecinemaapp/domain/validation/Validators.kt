package com.example.phonecinemaapp.domain.validation

fun validateEmail(email: String): String? {
    if (email.isBlank()) return "El email es obligatorio"
    val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    return if (!regex.toRegex().matches(email)) "Formato de email inválido" else null
}

fun validateName(name: String): String? {
    if (name.isBlank()) return "El nombre es obligatorio"
    if (name.length < 3) return "El nombre debe tener mínimo 3 letras"
    // Primera letra debe ser mayúscula
    if (!name.first().isUpperCase()) return "La primera letra debe ser mayúscula"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if (!regex.matches(name)) "Solo letras" else null
}

fun validatePassword(password: String): String? {
    if (password.isBlank()) return "La contraseña es obligatoria"
    if (password.length < 8) return "La contraseña debe tener mínimo 8 caracteres"
    if (!password.any { it.isUpperCase() }) return "La contraseña debe incluir una mayúscula"
    if (!password.any { it.isLowerCase() }) return "La contraseña debe incluir una minúscula"
    if (!password.any { it.isDigit() }) return "La contraseña debe incluir un número"
    if (!password.any { !it.isLetterOrDigit() }) return "La contraseña debe incluir un símbolo"
    if (password.contains(' ')) return "La contraseña no debe contener espacios"
    return null
}

fun validatePasswordConfirm(pass: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirma tu contraseña"
    return if (pass != confirm) "Las contraseñas no coinciden" else null
}

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
