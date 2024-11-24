package cr.ac.utn.dogview.data.network

import cr.ac.utn.dogview.data.models.BreedsModel
import cr.ac.utn.dogview.data.models.BreedImagesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsModel>

    @GET("breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): Response<BreedImagesModel>
}
