package com.example.doggyauth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val DOG_API_BASE_URL = "https://dog.ceo/"
    private const val AUTH_API_BASE_URL = "https://apicontainers.azurewebsites.net/"

    val dogApiService: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(DOG_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }

    val authApiService: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(AUTH_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
}
