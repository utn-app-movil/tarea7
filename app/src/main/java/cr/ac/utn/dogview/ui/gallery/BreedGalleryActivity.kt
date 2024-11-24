package cr.ac.utn.dogview.ui.gallery

import android.os.Bundle
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.dogview.R
import cr.ac.utn.dogview.data.network.ApiClient
import cr.ac.utn.dogview.data.repository.BreedsRepository
import cr.ac.utn.dogview.viewmodel.BreedGalleryViewModel
import cr.ac.utn.dogview.viewmodel.BreedViewModelFactory

class BreedGalleryActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var progressBar: ProgressBar

    private val viewModel: BreedGalleryViewModel by viewModels {
        BreedViewModelFactory(BreedsRepository(ApiClient.dogApi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed_gallery)

        // Inicializar vistas
        gridView = findViewById(R.id.gridViewImages)
        progressBar = findViewById(R.id.progressBarGallery)

        // Obtener el nombre de la raza seleccionada
        val breedName = intent.getStringExtra("breedName") ?: return

        // Observar datos
        observeViewModel()

        // Solicitar las imágenes de la raza
        viewModel.fetchBreedImages(breedName)
    }

    private fun observeViewModel() {
        viewModel.breedImages.observe(this) { response ->
            progressBar.visibility = ProgressBar.GONE
            if (response.isSuccessful) {
                val images = response.body()?.message ?: emptyList()

                val adapter = ImageAdapter(this, images)
                gridView.adapter = adapter
            } else {
                Toast.makeText(this, "Error al cargar imágenes: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) { error ->
            progressBar.visibility = ProgressBar.GONE
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}
