package com.example.doggyauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.etUsername)
        val password = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)

        loginButton.setOnClickListener {
            val user = username.text.toString().trim()
            val pass = password.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d(TAG, "Iniciando login con usuario: $user")

            val loginRequest = LoginRequest(user, pass)

            RetrofitClient.authApiService.login(loginRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            Log.d(TAG, "Respuesta exitosa: $loginResponse")

                            if (loginResponse?.data?.isActive == true) {
                                Log.d(TAG, "Usuario activo, redirigiendo a BreedsActivity")
                                val intent = Intent(this@LoginActivity, BreedsActivity::class.java)
                                intent.putExtra("USER_NAME", loginResponse.data.name) // Pasar datos adicionales si es necesario
                                startActivity(intent)
                                finish()
                            } else {
                                val message = loginResponse?.message ?: "Usuario inactivo o credenciales inválidas"
                                Log.w(TAG, "Login fallido: $message")
                                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.e(TAG, "Error en la respuesta: Código ${response.code()} - ${response.message()}")
                            Toast.makeText(this@LoginActivity, "Error en el servidor", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e(TAG, "Fallo en la conexión: ${t.message}", t)
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
