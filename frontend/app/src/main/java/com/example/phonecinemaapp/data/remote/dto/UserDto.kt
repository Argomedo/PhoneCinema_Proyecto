import com.example.phonecinemaapp.data.remote.dto.RolDto
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("idUsuario") val id: Long,
    val nombre: String,
    val email: String,
    val fotoPerfilUrl: String?,
    val rol: RolDto
)



