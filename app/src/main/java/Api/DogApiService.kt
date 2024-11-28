import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    fun getDogBreeds(): Call<DogBreedsResponse>

    @GET("breed/{breed}/images")
    fun getDogImages(@Path("breed") breed: String): Call<DogImageResponse>
}

data class DogImageResponse(val message: List<String>, val status: String)

