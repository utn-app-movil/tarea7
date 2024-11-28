package com.example.investigacion02

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.investigacion02.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.RegisterRequest
import network.RetrofitClient

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val id = binding.etRegisterId.text.toString().trim()
            val name = binding.etRegisterName.text.toString().trim()
            val lastName = binding.etRegisterLastName.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString().trim()

            if (id.isEmpty() || name.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            } else {
                val registerRequest = RegisterRequest(
                    id = id,
                    name = name,
                    lastName = lastName,
                    password = password
                )
                registerUser(registerRequest)
            }
        }
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.registerUser(registerRequest)
                runOnUiThread {
                    if (response.isSuccessful && response.body()?.responseCode == 0) {
                        Toast.makeText(this@RegisterActivity, getString(R.string.registration_successful),
                            Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val errorMessage = response.body()?.message ?: getString(R.string.registration_failed)
                        Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, getString(R.string.error_message,
                        e.localizedMessage), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
