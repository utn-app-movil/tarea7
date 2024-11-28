package com.blopix.login_dogs_api.network.model

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)
