package com.example.Note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.Note
import com.example.models.NoteResponse
import com.example.utils.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    val noteRepository: NoteRepository
):ViewModel() {

    var _noteResponse=MutableLiveData<NetworkResult<NoteResponse>>()
    val notelive:LiveData<NetworkResult<NoteResponse>>
        get() = _noteResponse

    fun getNote(){
        viewModelScope.launch {
            var response=noteRepository.getNote()

        }
    }
}