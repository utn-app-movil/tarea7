package com.example.tarea7.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://apicontainers.azurewebsites.net/"
    val authInstance: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    private const val DOG_BASE_URL = "https://dog.ceo/api/"
    val dogInstance: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(DOG_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }
}
