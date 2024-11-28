package com.example.doggyauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val gridView = findViewById<GridView>(R.id.imagesGridView)
        val breed = intent.getStringExtra("breed") ?: run {
            Log.e("GalleryActivity", "No se recibió ninguna raza")
            finish()
            return
        }
        Log.d("GalleryActivity", "Mostrando imágenes de la raza: $breed")

        title = "Imágenes de $breed"

        RetrofitClient.dogApiService.getBreedImages(breed).enqueue(object : Callback<ImagesResponse> {
            override fun onResponse(call: Call<ImagesResponse>, response: Response<ImagesResponse>) {
                if (response.isSuccessful) {
                    val images = response.body()?.message ?: emptyList()
                    val adapter = ImageAdapter(this@GalleryActivity, images)
                    gridView.adapter = adapter
                } else {
                    Toast.makeText(this@GalleryActivity, "Error al cargar imágenes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                Toast.makeText(this@GalleryActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
