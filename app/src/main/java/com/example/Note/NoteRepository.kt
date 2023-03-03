package com.example.Note

import com.example.api.NotesApi
import com.example.models.Note
import com.example.models.NoteRequest
import com.example.models.NoteResponse
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(
    val notesApi: NotesApi
){
    suspend fun getNote():Response<NoteResponse>{
        return notesApi.getNote();
    }

    suspend fun createNote(noteRequest: NoteRequest):Response<Note>{
        return notesApi.createNote(noteRequest)
    }

    suspend fun updateNote(id:String,noteRequest: NoteRequest):Response<Note>{
        return notesApi.updateNote(id,noteRequest)
    }

    suspend fun deleteNode(id:String):Response<Note>{
        return notesApi.deleteNote(id)
    }
}