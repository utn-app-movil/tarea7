package com.example.dogapp.ui.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.data.model.BreedsResponse
import com.example.dogapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

class BreedsViewModel : ViewModel() {
    private val _breeds = MutableLiveData<Map<String, List<String>>?>()
    val breeds: LiveData<Map<String, List<String>>?> get() = _breeds

    fun getBreeds() {
        viewModelScope.launch {
            val response = RetrofitClient.api.getBreeds()
            if (response.isSuccessful) {
                _breeds.postValue(response.body()?.message)
            } else {
                _breeds.postValue(null)
            }
        }
    }
}
