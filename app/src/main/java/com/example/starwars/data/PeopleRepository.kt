import com.example.starwars.data.PeopleResponse
import retrofit2.Response

class PeopleRepository {

    suspend fun getPeopleResponse(): Response<PeopleResponse> =
        PeopleService.peopleService.getPeopleResponse()

}