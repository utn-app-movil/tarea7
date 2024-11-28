package com.example.doggyauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breeds)

        val listView = findViewById<ListView>(R.id.breedsListView)

        RetrofitClient.dogApiService.getBreeds()
            .enqueue(object : Callback<BreedsResponse> {
                override fun onResponse(call: Call<BreedsResponse>, response: Response<BreedsResponse>) {
                    if (response.isSuccessful) {
                        val breeds = response.body()?.message?.keys?.toList() ?: emptyList()

                        val adapter = ArrayAdapter(this@BreedsActivity, android.R.layout.simple_list_item_1, breeds)
                        listView.adapter = adapter

                        listView.setOnItemClickListener { _, _, position, _ ->
                            val selectedBreed = listView.adapter.getItem(position) as String
                            Log.d("BreedsActivity", "Raza seleccionada: $selectedBreed")
                            val intent = Intent(this@BreedsActivity, GalleryActivity::class.java).apply {
                                putExtra("breed", selectedBreed)
                            }
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@BreedsActivity, "Error en la respuesta: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<BreedsResponse>, t: Throwable) {
                    Toast.makeText(this@BreedsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
}}
