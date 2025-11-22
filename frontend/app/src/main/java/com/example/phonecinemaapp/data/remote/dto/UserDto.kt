import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("idUsuario")
    val id: Long,
    val nombre: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val fotoPerfilUrl: String?,
    val rol: String
)
