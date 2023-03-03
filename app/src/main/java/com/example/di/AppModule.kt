package com.example.di

import com.example.api.AuthInterceptor
import com.example.api.NotesApi
import com.example.api.userApi
import com.example.utils.Constants.Companion.base_url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())


    @Provides
    @Singleton
    fun providesUserApi(retrofitBuilder: Retrofit.Builder): userApi =
        retrofitBuilder.build().create(userApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    // Now for the notes api we have to add a okHttpClient in retrofit instance we can do this in two ways
    //1- by creating an another retrofit instance for the notesApi and adding a client

//    fun provideRetrofit1(okHttpClient: OkHttpClient): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(base_url)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

    //2------------------

    // 2 method is we do not return a retrofit object instead of it we return a Retrofit Builder and creates it object using .build() function whenever required ]

    @Provides
    @Singleton
    fun providesNoteApi(retrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient): NotesApi =
        retrofitBuilder
            .client(okHttpClient)
            .build().create(NotesApi::class.java)
}