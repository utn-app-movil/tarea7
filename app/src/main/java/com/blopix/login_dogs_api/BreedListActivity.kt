package com.blopix.login_dogs_api

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blopix.login_dogs_api.network.DogApiClient
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BreedListActivity : AppCompatActivity() {

    private lateinit var listViewBreeds: ListView
    private lateinit var breedList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed_list)

        listViewBreeds = findViewById(R.id.listViewBreeds)
        breedList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breedList)
        listViewBreeds.adapter = adapter

        fetchBreeds()

        // Maneja la selección de una raza
        listViewBreeds.setOnItemClickListener { _, _, position, _ ->
            val breedName = breedList[position]
            val intent = Intent(this, BreedImagesActivity::class.java)
            intent.putExtra("breedName", breedName)
            startActivity(intent)
        }
    }

    private fun fetchBreeds() {
        lifecycleScope.launch {
            try {
                val response = DogApiClient.api.getBreeds()
                if (response.isSuccessful) {
                    val breeds = response.body()?.message ?: emptyMap()
                    breedList.clear()
                    breedList.addAll(breeds.keys)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@BreedListActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@BreedListActivity, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@BreedListActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
