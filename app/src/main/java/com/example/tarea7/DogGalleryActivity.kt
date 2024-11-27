package com.example.tarea7

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea7.network.DogImagesResponse
import com.example.tarea7.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogGalleryActivity : AppCompatActivity() {

    private lateinit var galleryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_gallery)

        galleryRecyclerView = findViewById(R.id.galleryRecyclerView)
        galleryRecyclerView.layoutManager = LinearLayoutManager(this)

        val selectedBreedName = intent.getStringExtra("BREED_NAME") ?: return
        loadDogImages(selectedBreedName)
    }

    private fun loadDogImages(breedName: String) {
        val imageCall = RetrofitClient.dogInstance.getDogImages(breedName)

        imageCall.enqueue(object : Callback<DogImagesResponse> {
            override fun onResponse(
                call: Call<DogImagesResponse>,
                response: Response<DogImagesResponse>
            ) {
                if (response.isSuccessful) {
                    val imageList = response.body()?.message ?: emptyList()
                    galleryRecyclerView.adapter = DogGalleryAdapter(imageList)
                } else {
                    Toast.makeText(
                        this@DogGalleryActivity,
                        getString(R.string.failed_fetch_images),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<DogImagesResponse>, t: Throwable) {
                Toast.makeText(
                    this@DogGalleryActivity,
                    getString(R.string.error_connection, t.message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
