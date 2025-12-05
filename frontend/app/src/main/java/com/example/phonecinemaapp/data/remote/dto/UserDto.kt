import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Long,
    val nombre: String,
    val email: String,
    val rol: String,
    val fotoPerfilUrl: String?,
    val token: String?
)

