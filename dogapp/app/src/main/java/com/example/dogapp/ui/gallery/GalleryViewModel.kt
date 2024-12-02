package com.example.dogapp.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.data.model.ImagesResponse
import com.example.dogapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {
    private val _images = MutableLiveData<List<String>?>()
    val images: LiveData<List<String>?> get() = _images

    fun getBreedImages(breed: String) {
        viewModelScope.launch {
            val response = RetrofitClient.api.getBreedImages(breed)
            if (response.isSuccessful) {
                _images.postValue(response.body()?.message)
            } else {
                _images.postValue(null)
            }
        }
    }
}
