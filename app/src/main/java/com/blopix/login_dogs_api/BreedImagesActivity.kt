package com.blopix.login_dogs_api

import adapter.ImageAdapter
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blopix.login_dogs_api.network.DogApiClient
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BreedImagesActivity : AppCompatActivity() {

    private lateinit var gridViewImages: GridView
    private lateinit var imageList: ArrayList<String>
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed_images)

        gridViewImages = findViewById(R.id.gridViewImages)
        imageList = ArrayList()

        val breedName = intent.getStringExtra("breedName")
        breedName?.let {
            fetchBreedImages(it)
        } ?: run {
            Toast.makeText(this, "No breed selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchBreedImages(breedName: String) {
        lifecycleScope.launch {
            try {
                val response = DogApiClient.api.getBreedImages(breedName)
                if (response.isSuccessful) {
                    val images = response.body()?.message ?: emptyList()
                    imageList.clear()
                    imageList.addAll(images)
                    adapter = ImageAdapter(this@BreedImagesActivity, imageList)
                    gridViewImages.adapter = adapter
                } else {
                    Toast.makeText(this@BreedImagesActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@BreedImagesActivity, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@BreedImagesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
