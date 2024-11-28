package com.blopix.login_dogs_api.network.model

open class LoginResponse(
    val data: UserData?,
    val responseCode: Int,
    val message: String
)