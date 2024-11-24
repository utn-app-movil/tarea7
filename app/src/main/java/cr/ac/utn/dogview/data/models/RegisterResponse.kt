package cr.ac.utn.dogview.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val data: UserData?,
    val responseCode: Int,
    val message: String?
)
