package cr.ac.utn.dogview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.utn.dogview.data.models.BreedImagesModel
import cr.ac.utn.dogview.data.repository.BreedsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * ViewModel para manejar la lógica de la galería de imágenes de razas de perros.
 */
class BreedGalleryViewModel(private val repository: BreedsRepository) : ViewModel() {

    private val _breedImages = MutableLiveData<Response<BreedImagesModel>>()
    val breedImages: LiveData<Response<BreedImagesModel>> get() = _breedImages

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchBreedImages(breed: String) {
        viewModelScope.launch {
            try {
                val response = repository.getBreedImages(breed)
                if (response.isSuccessful) {
                    _breedImages.postValue(response)
                } else {
                    _error.postValue("Error al cargar imágenes: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message}")
            }
        }
    }
}
