import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.People

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(    private val peopleRepository : PeopleRepository) : ViewModel() {



    //private val mutablePeopleData = MutableLiveData<List<People>>()
    //val immutablePeopleData: LiveData<List<People>> = mutablePeopleData

    // nowa wersja
    private val mutablePeopleData = MutableLiveData<UiState<List<People>>>()
    val immutablePeopleData: LiveData<UiState<List<People>>> = mutablePeopleData



    fun getData() {
        mutablePeopleData.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = peopleRepository.getPeopleResponse()
                val people = request.body()?.results
                //mutablePeopleData.postValue(people)
                mutablePeopleData.postValue(UiState(data = people, isLoading = false))


            } catch (e: Exception) {
                Log.e("MainViewModel", "Operacja nie powiodla sie", e)
            }
        }
    }

    val filterQuery = MutableLiveData("")

    fun updateFilterQuery(text: String){
        filterQuery.postValue(text)
    }
}