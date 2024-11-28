package com.example.investigacion02

import adapters.BreedListAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investigacion02.databinding.ActivityBreedListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.RetrofitClient
import util.Util

class BreedListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.getDogBreeds()

                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    val breeds = breedsResponse?.message?.keys?.toList() ?: emptyList()

                    runOnUiThread {
                        binding.recyclerView.adapter = BreedListAdapter(breeds) { breed ->
                            Util.openActivity(this@BreedListActivity, BreedGalleryActivity::class.java, "breed", breed)
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@BreedListActivity,
                            getString(R.string.failed_to_fetch_breeds), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@BreedListActivity, getString(R.string.error_message, e.localizedMessage),
                        Toast.LENGTH_SHORT).show()
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
