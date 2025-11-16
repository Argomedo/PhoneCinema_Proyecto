package com.example.phonecinemaapp.domain.validation

import android.util.Patterns // Usamos el patrón estándar de Android para emails

// Valida que el email no esté vacío y cumpla patrón de email (igual que el profe)
fun validateEmail(email: String): String? {
    if (email.isBlank()) return "El email es obligatorio"
    val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return if (!ok) "Formato de email inválido" else null
}

// Valida que el nombre contenga solo letras y espacios (sin números) – lógica del profe
fun validateName(name: String): String? {
    if (name.isBlank()) return "El nombre es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if (!regex.matches(name)) "Solo letras y espacios" else null
}

// Versión opcional si necesitas validar teléfono igual que el profe
fun validatePhoneDigitsOnly(phone: String): String? {
    if (phone.isBlank()) return "El teléfono es obligatorio"
    if (!phone.all { it.isDigit() }) return "Solo números"
    if (phone.length !in 8..15) return "Debe tener entre 8 y 15 dígitos"
    return null
}

// Valida seguridad de la contraseña (mín. 8, mayús, minús, número y símbolo; sin espacios) – igual que el profe
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

// Valida que la confirmación coincida con la contraseña – igual que el profe
fun validatePasswordConfirm(pass: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirma tu contraseña"
    return if (pass != confirm) "Las contraseñas no coinciden" else null
}

// Valida todo el formulario completo usando las funciones anteriores
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
