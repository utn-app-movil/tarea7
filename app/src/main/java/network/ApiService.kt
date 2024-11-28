package network

import models.DogBreedsResponse
import models.DogImagesResponse
import models.RegisterRequest
import models.ResponseModel
import models.Technician
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("technicians/validateAuth")
    suspend fun validateAuth(@Body credentials: Map<String, String>): Response<ResponseModel<Technician>>

    @POST("technicians")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<ResponseModel<Unit>>

    @GET("https://dog.ceo/api/breeds/list/all")
    suspend fun getDogBreeds(): Response<DogBreedsResponse>

    @GET("https://dog.ceo/api/breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): Response<DogImagesResponse>
}

