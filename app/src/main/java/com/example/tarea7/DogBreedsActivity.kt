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

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breeds)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchDogBreeds()
    }

    private fun fetchDogBreeds() {
        val call = RetrofitClient.dogInstance.getDogBreeds()

        call.enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(
                call: Call<DogBreedsResponse>,
                response: Response<DogBreedsResponse>
            ) {
                if (response.isSuccessful) {
                    val breeds = response.body()?.message?.keys?.toList() ?: emptyList()
                    val adapter = DogBreedAdapter(breeds) { breed ->
                        util.openActivity(
                            this@DogBreedsActivity,
                            DogGalleryActivity::class.java,
                            "BREED_NAME",
                            breed
                        )
                    }
                    recyclerView.adapter = adapter
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
