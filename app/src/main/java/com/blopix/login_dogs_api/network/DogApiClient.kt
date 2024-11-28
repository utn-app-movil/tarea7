package com.blopix.login_dogs_api.network

import com.blopix.login_dogs_api.network.model.DogApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogApiClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    val api: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }
}
