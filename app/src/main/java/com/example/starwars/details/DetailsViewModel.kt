package com.example.starwars.details

import PeopleDetailsRepository
import UiState
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.PeopleDetailsResponce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val peopleDetailsRepository = PeopleDetailsRepository()

    private val mutablePersonDetails = MutableLiveData<UiState<PeopleDetailsResponce>>()
    val immutablePersonDetails: MutableLiveData<UiState<PeopleDetailsResponce>>
        get() = mutablePersonDetails

    fun getData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mutablePersonDetails.postValue(UiState(isLoading = true))

                val request = peopleDetailsRepository.getPeopleDetailsResponse(id)

                    val detailsResponse = request.body()
                    if (detailsResponse != null) {
                        val details = PeopleDetailsResponce(
                            name = detailsResponse.name ?: "",
                            gender = detailsResponse.gender ?: "",
                            birth_year = detailsResponse.birth_year ?: "",
                            skin_color = detailsResponse.skin_color ?: "",
                            eye_color = detailsResponse.eye_color ?: ""
                        )
                        mutablePersonDetails.postValue(UiState(data = details, isLoading = false))

                } else {
                    // Handle unsuccessful response

                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Operation failed", e)
                // Handle other exceptions

            }
        }
    }
}