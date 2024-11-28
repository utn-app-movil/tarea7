package cr.ac.utn.tarea7

import Api.RetroClient
import DogBreedsAdapter
import DogBreedsResponse
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogBreedsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dogBreedsAdapter: DogBreedsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breeds)

        recyclerView = findViewById(R.id.recycler_View_Dog_Breeds)
        recyclerView.layoutManager = LinearLayoutManager(this)


        RetroClient.instance.getDogBreeds().enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {

                    val breeds = response.body()?.message?.keys?.toList() ?: emptyList()

                    dogBreedsAdapter = DogBreedsAdapter(breeds) { selectedBreed ->
                        openDogGallery(selectedBreed)
                    }
                    recyclerView.adapter = dogBreedsAdapter
                } else {
                    Toast.makeText(this@DogBreedsActivity, "Error al cargar las razas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                Toast.makeText(this@DogBreedsActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun openDogGallery(breed: String) {
        val intent = Intent(this, DogGalleryActivity::class.java)
        intent.putExtra("breed", breed)
        startActivity(intent)
    }
}
