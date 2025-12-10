import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinemaapp.data.remote.dto.RatingResponse
import retrofit2.http.*

interface ReviewApi {

    @GET("reviews/movie/{movieId}")
    suspend fun getByMovie(@Path("movieId") movieId: Long): List<ReviewDto>

    @POST("reviews")
    suspend fun create(@Body body: ReviewDto): ReviewDto

    @GET("reviews")
    suspend fun getAll(): List<ReviewDto>

    @DELETE("reviews/{id}")
    suspend fun delete(@Path("id") id: Long)

    @GET("reviews/promedio/{movieId}")
    suspend fun getPromedio(@Path("movieId") movieId: Long): RatingResponse

}
