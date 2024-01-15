import com.example.starwars.data.PeopleDetailsResponce
import retrofit2.Response

class PeopleDetailsRepository {

    suspend fun getPeopleDetailsResponse(id: String): Response<PeopleDetailsResponce> =
        PeopleService.peopleService.getPearsonDetails(id)

}