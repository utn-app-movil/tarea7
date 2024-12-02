package com.example.dogapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.data.model.LoginRequest
import com.example.dogapp.data.model.LoginResponse
import com.example.dogapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            val response = RetrofitClient.api.validateAuth(request)
            if (response.isSuccessful) {
                _loginResponse.postValue(response.body())
            } else {
                _loginResponse.postValue(null)
            }
        }
    }
}
