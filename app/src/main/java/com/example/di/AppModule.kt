package com.example.di

import com.example.api.NotesApi
import com.example.api.userApi
import com.example.utils.Constants.Companion.base_url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesUserApi(retrofit: Retrofit): userApi =
        retrofit.create(userApi::class.java)

    @Provides
    @Singleton
    fun providesNoteApi(retrofit: Retrofit): NotesApi =
        retrofit.create(NotesApi::class.java)
}