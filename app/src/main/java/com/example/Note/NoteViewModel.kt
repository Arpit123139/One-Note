package com.example.Note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.Note
import com.example.models.NoteRequest
import com.example.models.NoteResponse
import com.example.models.userRequest
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

    var _statusResponse=MutableLiveData<NetworkResult<Note>>()
    val statusLive:LiveData<NetworkResult<Note>>
    get() = _statusResponse

    fun getNote(){
        viewModelScope.launch {
            _noteResponse.value= NetworkResult.Loading()
            var response=noteRepository.getNote()

            if (response.isSuccessful && response.body() != null) {
                _noteResponse.value = NetworkResult.Success(response.body()!!)
            } else if (response.errorBody() != null) {        //ERROR BODY HAS A JSON
                //parsing the JSON
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _noteResponse.value = NetworkResult.Error(errorObj.getString("message"))
            } else {
                _noteResponse.value = NetworkResult.Error("Something Went Wrong")
            }

        }
    }

    fun createNote(noteRequest: NoteRequest){

        viewModelScope.launch {
            _statusResponse.value=NetworkResult.Loading()
            val response=noteRepository.createNote(noteRequest)
            handleResponse(response)
        }
    }

    fun deleteNode(id:String){
        viewModelScope.launch {
            _statusResponse.value=NetworkResult.Loading()
            val response=noteRepository.deleteNode(id)
            handleResponse(response)
        }
    }

    fun updateNode(id:String,noteRequest: NoteRequest){

        viewModelScope.launch {
            _statusResponse.value=NetworkResult.Loading()
            val response=noteRepository.updateNote(id,noteRequest)
            handleResponse(response)
        }
    }

    private fun handleResponse(noteResponse: Response<Note>) {
        if (noteResponse.isSuccessful && noteResponse.body() != null) {
            _statusResponse.value = NetworkResult.Success(noteResponse.body()!!)
        } else if (noteResponse.errorBody() != null) {        //ERROR BODY HAS A JSON
            //parsing the JSON
            val errorObj = JSONObject(noteResponse.errorBody()!!.charStream().readText())
            _statusResponse.value = NetworkResult.Error(errorObj.getString("message"))
        } else {
            _statusResponse.value = NetworkResult.Error("Something Went Wrong")
        }
    }
    
    
}