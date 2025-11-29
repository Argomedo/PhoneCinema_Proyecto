import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("idUsuario") val id: Long,
    val nombre: String,
    val email: String,
    val rol: String,
    val fotoPerfilUrl: String?,
    val token: String?
)

