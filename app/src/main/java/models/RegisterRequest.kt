package models

data class RegisterRequest(
    val id: String,
    val name: String,
    val lastName: String,
    val password: String,
    val isActive: Boolean = true,
    val isTemporary: Boolean = true
)
