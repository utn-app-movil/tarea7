package utn.cr.tarea07

import Interface.Client.postApiService
import Model.PostRequest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class login_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val id_txt = findViewById<EditText>(R.id.txt_id)
        val pass_txt = findViewById<EditText>(R.id.txt_pass)
        val btnLo = findViewById<Button>(R.id.btn_Reg)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnLo.setOnClickListener {
            val id = id_txt.text.toString().trim()
            val pass = pass_txt.text.toString().trim()

            if (id.isNotEmpty() && pass.isNotEmpty()) {
                val loginRequest = PostRequest(id = id, password = pass)

                lifecycleScope.launch {
                    try {
                        val response =
                            withContext(Dispatchers.IO) { postApiService.validateAuth(loginRequest) }
                        if (response.responseCode == 0 && response.data?.isActive == true) {
                            Toast.makeText(
                                this@login_act,
                                "Welcome ${response.data.name} ${response.data.lastName}",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@login_act, listPets::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@login_act, response.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@login_act,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this, R.string.msgError, Toast.LENGTH_SHORT).show()
            }
        }

    }

    }