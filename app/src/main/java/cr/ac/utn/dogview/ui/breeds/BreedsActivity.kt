package cr.ac.utn.dogview.ui.breeds

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.dogview.R
import cr.ac.utn.dogview.data.network.ApiClient
import cr.ac.utn.dogview.data.repository.BreedsRepository
import cr.ac.utn.dogview.ui.gallery.BreedGalleryActivity
import cr.ac.utn.dogview.viewmodel.BreedViewModelFactory
import cr.ac.utn.dogview.viewmodel.BreedsViewModel

class BreedsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar

    private val viewModel: BreedsViewModel by viewModels {
        BreedViewModelFactory(BreedsRepository(ApiClient.dogApi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breeds)

        // Inicializar vistas
        listView = findViewById(R.id.listViewBreeds)
        progressBar = findViewById(R.id.progressBar)

        // Observar datos
        observeViewModel()

        // Solicitar las razas
        viewModel.fetchBreeds()
    }

    private fun observeViewModel() {
        viewModel.breeds.observe(this) { response ->
            progressBar.visibility = View.GONE
            if (response.isSuccessful) {
                val breedsMap = response.body()?.message ?: emptyMap()
                val breedsList = breedsMap.keys.toList()

                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breedsList)
                listView.adapter = adapter

                listView.setOnItemClickListener { _, _, position, _ ->
                    val selectedBreed = breedsList[position]
                    val intent = Intent(this, BreedGalleryActivity::class.java)
                    intent.putExtra("breedName", selectedBreed)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Error al cargar razas: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) { error ->
            progressBar.visibility = View.GONE
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}
