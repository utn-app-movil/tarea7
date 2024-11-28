package Api

import DogApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    val instance: DogApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(DogApiService::class.java)
    }
}