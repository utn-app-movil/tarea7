package com.blopix.login_dogs_api.network.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): Response<BreedsResponse>

    @GET("breed/{breedName}/images")
    suspend fun getBreedImages(@Path("breedName") breedName: String): Response<BreedImagesResponse>
}
