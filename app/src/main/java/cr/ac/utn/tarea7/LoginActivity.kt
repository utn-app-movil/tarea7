package cr.ac.utn.tarea7

import Api.ApiClient
import ApiService
import TechnicianRequest
import TechnicianResponse
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUserId = findViewById<EditText>(R.id.ID_User)
        val etPassword = findViewById<EditText>(R.id.User_Password)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val UserId = etUserId.text.toString()
            val User_Password = etPassword.text.toString()

            if (UserId.isNotEmpty() && User_Password.isNotEmpty()) {
                val apiService = ApiClient.instance.create(ApiService::class.java)
                val request = TechnicianRequest(UserId, User_Password)

                apiService.validateAuth(request).enqueue(object : Callback<TechnicianResponse> {
                    override fun onResponse(
                        call: Call<TechnicianResponse>,
                        response: Response<TechnicianResponse>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body?.data != null && body.data.isActive) {
                                val intent = Intent(this@LoginActivity, DogBreedsActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, body?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<TechnicianResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
