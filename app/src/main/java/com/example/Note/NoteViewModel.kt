package com.example.Note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.Note
import com.example.models.NoteResponse
import com.example.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val noteRepository: NoteRepository
):ViewModel() {

    var _noteResponse=MutableLiveData<NetworkResult<NoteResponse>>()
    val notelive:LiveData<NetworkResult<NoteResponse>>
        get() = _noteResponse

    fun getNote(){
        viewModelScope.launch {
            _noteResponse.value= NetworkResult.Loading()
            var response=noteRepository.getNote()
            handleResponse(response)

        }
    }

    private fun handleResponse(noteResponse: Response<NoteResponse>) {
        if (noteResponse.isSuccessful && noteResponse.body() != null) {
            _noteResponse.value = NetworkResult.Success(noteResponse.body()!!)
        } else if (noteResponse.errorBody() != null) {        //ERROR BODY HAS A JSON
            //parsing the JSON
            val errorObj = JSONObject(noteResponse.errorBody()!!.charStream().readText())
            _noteResponse.value = NetworkResult.Error(errorObj.getString("message"))
        } else {
            _noteResponse.value = NetworkResult.Error("Something Went Wrong")
        }
    }
    
    
}