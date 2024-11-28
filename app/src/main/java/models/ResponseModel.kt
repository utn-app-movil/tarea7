package models

data class ResponseModel<T>(
    val data: T?,
    val responseCode: Int,
    val message: String?
)
