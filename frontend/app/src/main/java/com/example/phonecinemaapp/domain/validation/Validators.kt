package com.example.phonecinemaapp.domain.validation

fun validateEmail(email: String): String? {
    val e = email.trim().lowercase()

    if (e.isEmpty()) return "El email es obligatorio"
    if (e.length > 254) return "El email es demasiado largo"
    if (e.any { it.isWhitespace() }) return "El email no debe contener espacios"

    val parts = e.split("@")
    if (parts.size != 2) return "Formato de email inválido"

    val local = parts[0]
    val domain = parts[1]

    if (local.isEmpty() || local.length > 64) return "Formato de email inválido"
    if (domain.isEmpty() || domain.contains("..")) return "Formato de email inválido"

    val localRegex = Regex("^[a-z0-9+_.-]+$")
    if (!localRegex.matches(local)) return "Formato de email inválido"

    val labels = domain.split(".")
    if (labels.size < 2) return "Formato de email inválido"

    val labelRegex = Regex("^[a-z0-9-]+$")
    if (labels.any {
            it.isEmpty() ||
                    it.length > 63 ||
                    !labelRegex.matches(it) ||
                    it.startsWith("-") ||
                    it.endsWith("-")
        }) {
        return "Formato de email inválido"
    }

    val tld = labels.last()
    if (tld.length !in 2..24 || !Regex("^[a-z]+$").matches(tld))
        return "Formato de email inválido"

    return null
}

fun validateName(username: String): String? {
    val u = username.trim()

    if (u.isEmpty()) return "El nombre de usuario es obligatorio"
    if (u.length < 3) return "El nombre de usuario debe tener al menos 3 caracteres"
    if (u.length > 30) return "El nombre de usuario no puede superar los 30 caracteres"
    if (u.any { it.isWhitespace() }) return "El nombre de usuario no debe contener espacios"

    val regex = Regex("^[a-zA-Z0-9._-]+$")
    if (!regex.matches(u)) {
        return "El nombre de usuario solo puede contener letras, números, punto, guion y guion bajo"
    }

    if (u.startsWith(".") || u.startsWith("_") || u.startsWith("-"))
        return "El nombre de usuario es inválido"

    if (u.contains("..") || u.contains("__") || u.contains("--"))
        return "El nombre de usuario es inválido"

    return null
}

fun validatePassword(password: String): String? {
    if (password.isBlank()) return "La contraseña es obligatoria"
    if (password.length < 8) return "La contraseña debe tener mínimo 8 caracteres"
    if (password.length > 72) return "La contraseña no puede superar los 72 caracteres"
    if (password.any { it.isWhitespace() }) return "La contraseña no debe contener espacios"
    if (!password.any { it.isUpperCase() }) return "La contraseña debe incluir una mayúscula"
    if (!password.any { it.isLowerCase() }) return "La contraseña debe incluir una minúscula"
    if (!password.any { it.isDigit() }) return "La contraseña debe incluir un número"
    if (!password.any { !it.isLetterOrDigit() }) return "La contraseña debe incluir un símbolo"

    return null
}

fun validatePasswordConfirm(password: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirma tu contraseña"
    return if (password != confirm) "Las contraseñas no coinciden" else null
}

fun validateRegisterFields(
    username: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {
    validateName(username)?.let { return it }
    validateEmail(email)?.let { return it }
    validatePassword(password)?.let { return it }
    validatePasswordConfirm(password, confirmPassword)?.let { return it }
    return null
}
