package com.example.doggyauth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApiService {
    @POST("technicians/validateAuth")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
