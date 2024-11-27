package com.example.tarea7

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea7.network.RetrofitClient
import cr.ac.utn.appmovil.util.EXTRA_MESSAGE_ID
import data.LoginResponse
import cr.ac.utn.appmovil.util.util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var inputUsername: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputUsername = findViewById(R.id.usernameEditText)
        inputPassword = findViewById(R.id.passwordEditText)
        buttonLogin = findViewById(R.id.loginButton)
        buttonRegister = findViewById(R.id.registerButton)

        buttonLogin.setOnClickListener {
            attemptLogin()
        }

        buttonRegister.setOnClickListener {
            util.openActivity(this, RegisterActivity::class.java, EXTRA_MESSAGE_ID, null)
        }
    }

    private fun attemptLogin() {
        val usernameValue = inputUsername.text.toString()
        val passwordValue = inputPassword.text.toString()

        if (usernameValue.isEmpty() || passwordValue.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_credentials), Toast.LENGTH_SHORT).show()
            return
        }

        val userCredentials = mapOf("id" to usernameValue, "password" to passwordValue)

        RetrofitClient.authInstance.validateAuth(userCredentials)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val loginResult = response.body()
                        when (loginResult?.responseCode) {
                            0 -> {
                                val technicianData = loginResult.data
                                Toast.makeText(
                                    this@LoginActivity,
                                    getString(
                                        R.string.welcome_user,
                                        technicianData?.name,
                                        technicianData?.lastName
                                    ),
                                    Toast.LENGTH_SHORT
                                ).show()

                                util.openActivity(
                                    this@LoginActivity,
                                    DogBreedsActivity::class.java,
                                    EXTRA_MESSAGE_ID,
                                    null
                                )
                                finish()
                            }
                            -1 -> {
                                Toast.makeText(
                                    this@LoginActivity,
                                    loginResult.message ?: getString(R.string.inactive_user),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                Toast.makeText(
                                    this@LoginActivity,
                                    getString(R.string.invalid_credentials),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.server_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, throwable: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.connection_error, throwable.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
