package com.example.tarea7

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea7.network.DogBreedsResponse
import com.example.tarea7.network.RetrofitClient
import cr.ac.utn.appmovil.util.util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogBreedsActivity : AppCompatActivity() {

    private lateinit var breedRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breeds)

        breedRecyclerView = findViewById(R.id.recyclerView)
        breedRecyclerView.layoutManager = LinearLayoutManager(this)

        loadDogBreeds()
    }

    private fun loadDogBreeds() {
        val breedCall = RetrofitClient.dogInstance.getDogBreeds()

        breedCall.enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(
                call: Call<DogBreedsResponse>,
                response: Response<DogBreedsResponse>
            ) {
                if (response.isSuccessful) {
                    val breedList = response.body()?.message?.keys?.toList() ?: emptyList()
                    val breedAdapter = DogBreedAdapter(breedList) { breedName ->
                        util.openActivity(
                            this@DogBreedsActivity,
                            DogGalleryActivity::class.java,
                            "BREED_NAME",
                            breedName
                        )
                    }
                    breedRecyclerView.adapter = breedAdapter
                } else {
                    Toast.makeText(
                        this@DogBreedsActivity,
                        getString(R.string.failed_fetch_breeds),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                Toast.makeText(
                    this@DogBreedsActivity,
                    getString(R.string.error_connection, t.message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
