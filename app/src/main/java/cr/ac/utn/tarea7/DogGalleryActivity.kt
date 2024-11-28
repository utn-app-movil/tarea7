package cr.ac.utn.tarea7

import Api.RetroClient
import DogImageResponse
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogGalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var DGadapter: DogGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_gallery)

        recyclerView = findViewById(R.id.recycler_View_Dog_Gallery)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val breed = intent.getStringExtra("breed") ?: return

        fetchDogImages(breed)
    }

    private fun fetchDogImages(breed: String) {
        val apiService = RetroClient.instance
        apiService.getDogImages(breed).enqueue(object : Callback<DogImageResponse> {
            override fun onResponse(
                call: Call<DogImageResponse>,
                response: Response<DogImageResponse>
            ) {
                if (response.isSuccessful) {
                    val images = response.body()?.message ?: emptyList()
                    DGadapter = DogGalleryAdapter(images)
                    recyclerView.adapter = DGadapter
                } else {
                    Log.e("DogGalleryActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DogImageResponse>, t: Throwable) {
                Log.e("DogGalleryActivity", "Failure: ${t.message}")
            }
        })
    }
}
