package cr.ac.utn.dogview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.utn.dogview.data.models.BreedsModel
import cr.ac.utn.dogview.data.repository.BreedsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * ViewModel para manejar la lógica de la lista de razas de perros.
 */
class BreedsViewModel(private val repository: BreedsRepository) : ViewModel() {

    private val _breeds = MutableLiveData<Response<BreedsModel>>()
    val breeds: LiveData<Response<BreedsModel>> get() = _breeds

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val response = repository.getAllBreeds()
                if (response.isSuccessful) {
                    _breeds.postValue(response)
                } else {
                    _error.postValue("Error al cargar las razas: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message}")
            }
        }
    }
}
