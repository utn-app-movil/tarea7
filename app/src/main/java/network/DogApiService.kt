package com.example.tarea7.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

data class DogImagesResponse(
    val message: List<String>,
    val status: String
)

interface DogApiService {
    @GET("breeds/list/all")
    fun getDogBreeds(): Call<DogBreedsResponse>

    @GET("breed/{breed}/images")
    fun getDogImages(@Path("breed") breed: String): Call<DogImagesResponse>
}
