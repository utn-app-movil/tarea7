package cr.ac.utn.dogview.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: String,
    val name: String,
    val lastName: String,
    val password: String,
    val isActive: Boolean,
    val isTemporary: Boolean
)

