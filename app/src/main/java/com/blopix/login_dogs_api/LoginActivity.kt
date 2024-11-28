package com.blopix.login_dogs_api

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blopix.login_dogs_api.network.ApiClient.postApiService
import com.blopix.login_dogs_api.network.model.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUserId = findViewById<EditText>(R.id.etUserId)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val userId = etUserId.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (userId.isNotEmpty() && password.isNotEmpty()) {
                login(userId, password)
            } else {
                Toast.makeText(this, R.string.errorFields, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(userId: String, password: String) {
        val loginRequest = LoginRequest(id = userId, password = password)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = postApiService.validateAuth(loginRequest)
                withContext(Dispatchers.Main) {
                    if (response.responseCode == 0 && response.data?.isActive == true) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Welcome ${response.data.name} ${response.data.lastName}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@LoginActivity, BreedListActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
