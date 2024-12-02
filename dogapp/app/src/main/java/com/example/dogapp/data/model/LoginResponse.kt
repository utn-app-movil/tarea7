package com.example.dogapp.data.model

data class LoginResponse(
    val data: Technician?,
    val responseCode: Int,
    val message: String
)

data class Technician(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val isTemporary: Boolean,
    val password: String
)
