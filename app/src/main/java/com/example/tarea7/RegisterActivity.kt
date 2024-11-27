package com.example.tarea7

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea7.network.RegisterResponse
import com.example.tarea7.network.RetrofitClient
import com.example.tarea7.network.TechnicianData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var inputId: EditText
    private lateinit var inputName: EditText
    private lateinit var inputLastName: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        inputId = findViewById(R.id.idEditText)
        inputName = findViewById(R.id.nameEditText)
        inputLastName = findViewById(R.id.lastNameEditText)
        inputPassword = findViewById(R.id.passwordEditText)
        buttonRegister = findViewById(R.id.registerButton)

        buttonRegister.setOnClickListener {
            attemptRegistration()
        }
    }

    private fun attemptRegistration() {
        val technicianId = inputId.text.toString()
        val technicianName = inputName.text.toString()
        val technicianLastName = inputLastName.text.toString()
        val technicianPassword = inputPassword.text.toString()

        if (technicianId.isEmpty() || technicianName.isEmpty() || technicianLastName.isEmpty() || technicianPassword.isEmpty()) {
            Toast.makeText(this, getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show()
            return
        }

        val technicianDetails = TechnicianData(
            id = technicianId,
            name = technicianName,
            lastName = technicianLastName,
            isActive = true,
            password = technicianPassword,
            isTemporary = false
        )

        Log.d("RegisterData", technicianDetails.toString())

        RetrofitClient.authInstance.registerTechnician(technicianDetails)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val registrationResult = response.body()
                        if (registrationResult?.responseCode == 0) {
                            Toast.makeText(
                                this@RegisterActivity,
                                getString(R.string.register_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                registrationResult?.message
                                    ?: getString(R.string.register_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("RegisterError", errorBody)
                        Toast.makeText(
                            this@RegisterActivity,
                            getString(R.string.server_error_with_body, errorBody),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, throwable: Throwable) {
                    Toast.makeText(
                        this@RegisterActivity,
                        getString(R.string.connection_error, throwable.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
