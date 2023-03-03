package com.example.Note

import com.example.api.NotesApi
import com.example.models.NoteResponse
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(
    val notesApi: NotesApi
){
    suspend fun getNote():Response<NoteResponse>{
        return notesApi.getNote();
    }
}