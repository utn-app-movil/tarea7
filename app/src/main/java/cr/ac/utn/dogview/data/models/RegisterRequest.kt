package cr.ac.utn.dogview.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val id: String,
    val name: String,
    val lastName: String,
    val isActive: Boolean,
    val password: String,
    val isTemporary: Boolean
)

