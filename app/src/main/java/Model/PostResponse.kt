package Model

open class PostResponse (
    val data: User?,
    val responseCode: Int,
    val message: String
)