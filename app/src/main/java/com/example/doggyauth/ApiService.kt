package com.example.doggyauth

import retrofit2.Call
import retrofit2.http.*

data class LoginResponse(
    val data: UserData?,
    val message: String,
    val responseCode: Int
)

data class LoginRequest(
    val id: String,
    val password: String
)

data class UserData(
    val id: String,
    val isActive: Boolean,
    val lastName: String,
    val name: String
)

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

data class ImagesResponse(
    val message: List<String>,
    val status: String
)

interface ApiService {
    @POST("technicians/validateAuth")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("api/breeds/list/all")
    fun getBreeds(): Call<BreedsResponse>

    @GET("api/breed/{breed}/images")
    fun getBreedImages(@Path("breed") breed: String): Call<ImagesResponse>
}
