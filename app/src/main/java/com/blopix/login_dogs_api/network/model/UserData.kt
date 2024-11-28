package com.blopix.login_dogs_api.network.model

data class UserData(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val isTemporary: Boolean
)