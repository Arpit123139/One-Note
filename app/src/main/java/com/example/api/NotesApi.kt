package com.example.api

import com.example.models.NoteRequest
import com.example.models.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApi {

    @GET("note/getnote")
    suspend fun getNote(): Response<NoteResponse>

    @POST("note/addNote")
    suspend fun createNote(@Body noteRequest: NoteRequest):Response<NoteResponse>

    @PUT("note/update/{id}")
    suspend fun updateNote(@Path("id") id:String,@Body noteRequest: NoteRequest):Response<NoteResponse>

    @DELETE("note/delete/{id}")
    suspend fun deleteNote(@Path("id") id:String):Response<NoteResponse>

}