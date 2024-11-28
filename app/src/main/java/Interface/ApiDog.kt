package Interface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiDog {
    private const val BASE_URL = "https://dog.ceo/api/"

    val api: Dog by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Dog::class.java)
    }
}