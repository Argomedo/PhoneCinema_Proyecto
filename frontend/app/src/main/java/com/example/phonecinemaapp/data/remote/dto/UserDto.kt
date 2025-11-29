import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("idUsuario") val id: Long,
    val nombre: String,
    val email: String,
    @SerializedName("rol") val rol: RolDto,
    val fotoPerfilUrl: String?,
    val token: String?
)

data class RolDto(
    @SerializedName("nombre") val nombre: String
)

