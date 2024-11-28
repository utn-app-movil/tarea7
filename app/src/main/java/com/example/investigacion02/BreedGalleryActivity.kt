package com.example.investigacion02

import adapters.GalleryAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.investigacion02.databinding.ActivityBreedGalleryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.RetrofitClient
import util.Util

class BreedGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val breed = intent.getStringExtra("breed") ?: return

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.getBreedImages(breed)

                if (response.isSuccessful) {
                    val imagesResponse = response.body()
                    val images = imagesResponse?.message ?: emptyList()

                    runOnUiThread {
                        if (images.isNotEmpty()) {
                            binding.recyclerView.adapter = GalleryAdapter(images)
                        } else {
                            Toast.makeText(
                                this@BreedGalleryActivity, getString(R.string.no_images_found, breed),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@BreedGalleryActivity, getString(R.string.failed_to_load_images),
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@BreedGalleryActivity,
                        getString(R.string.error_message, e.localizedMessage), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Toast.makeText(this, getString(R.string.logged_out), Toast.LENGTH_SHORT).show()
                Util.openActivity(this, LoginActivity::class.java, null, null)
                finish()
                true
            }
            R.id.action_back -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

