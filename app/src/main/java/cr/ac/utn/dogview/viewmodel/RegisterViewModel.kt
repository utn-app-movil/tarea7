package cr.ac.utn.dogview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.utn.dogview.data.models.RegisterRequest
import cr.ac.utn.dogview.data.models.RegisterResponse
import cr.ac.utn.dogview.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * ViewModel para manejar el flujo de registro.
 */
class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<Response<RegisterResponse>>()
    val registerResult: LiveData<Response<RegisterResponse>> get() = _registerResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    /**
     * Registra un nuevo técnico en el sistema.
     * @param newTechnician Datos del técnico a registrar.
     */
    fun register(newTechnician: RegisterRequest) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repository.registerTechnician(
                    newTechnician.id,
                    newTechnician.name,
                    newTechnician.lastName,
                    newTechnician.password,
                    newTechnician.isActive,
                    newTechnician.isTemporary
                )
                if (response.isSuccessful) {
                    _registerResult.postValue(response)
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error de conexión: ${e.localizedMessage}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
