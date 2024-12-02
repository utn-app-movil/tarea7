package com.example.dogapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dogapp.data.model.LoginRequest
import com.example.dogapp.databinding.ActivityLoginBinding
import com.example.dogapp.ui.breeds.BreedsActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val id = binding.etUserId.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(LoginRequest(id, password))
        }

        viewModel.loginResponse.observe(this) { response ->
            if (response?.data != null && response.data.isActive) {
                startActivity(Intent(this, BreedsActivity::class.java))
            } else {
                Toast.makeText(this, response?.message ?: "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
