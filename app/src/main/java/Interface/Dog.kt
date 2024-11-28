package Interface

import Model.dogsImages
import Model.dogsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Dog {
    @GET("breeds/list/all")
    suspend fun getBreeds(): Response<dogsResponse>

    @GET("breed/{breedName}/images")
    suspend fun getBreedImages(@Path("breedName") breedName: String): Response<dogsImages>
}