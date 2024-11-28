package com.blopix.login_dogs_api.network

import com.blopix.login_dogs_api.network.model.LoginRequest
import com.blopix.login_dogs_api.network.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("technicians/validateAuth")
    suspend fun validateAuth(@Body loginRequest: LoginRequest): LoginResponse
}

