package com.example.doggyauth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("api/breeds/list/all")
    fun getBreeds(): Call<BreedsResponse>

    @GET("api/breed/{breed}/images")
    fun getBreedImages(@Path("breed") breed: String): Call<ImagesResponse>
}
