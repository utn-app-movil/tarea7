package Interface

import Model.PostRequest
import Model.PostResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCli {
    @POST("technicians/validateAuth")
    suspend fun validateAuth(@Body loginRequest: PostRequest): PostResponse
}