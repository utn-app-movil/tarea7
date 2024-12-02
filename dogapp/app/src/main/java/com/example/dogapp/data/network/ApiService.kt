package com.example.dogapp.data.network

import com.example.dogapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("technicians/validateAuth")
    suspend fun validateAuth(@Body request: LoginRequest): Response<LoginResponse>

    @GET("technicians")
    suspend fun getTechnicians(): Response<List<Technician>>

    @GET("https://dog.ceo/api/breeds/list/all")
    suspend fun getBreeds(): Response<BreedsResponse>

    @GET("https://dog.ceo/api/breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): Response<ImagesResponse>
}
