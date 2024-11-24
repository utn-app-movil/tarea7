package cr.ac.utn.dogview.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val id: String,
    val password: String
)



