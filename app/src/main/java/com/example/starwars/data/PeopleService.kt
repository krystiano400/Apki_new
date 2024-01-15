import com.example.starwars.data.PeopleDetailsResponce
import com.example.starwars.data.PeopleResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PeopleService {

    @GET("/api/people")
    suspend fun getPeopleResponse(): Response<PeopleResponse>

    @GET("/api/people/{id}")
    suspend fun getPearsonDetails(@Path("id") id: String): Response<PeopleDetailsResponce>

    companion object {
        private const val STAR_URL = "https://swapi.dev/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(STAR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val peopleService: PeopleService by lazy {
            retrofit.create(PeopleService::class.java)
        }
    }
}