package com.example.investigacion02

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.investigacion02.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.RetrofitClient
import util.Util

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val id = binding.etUserId.text?.toString()?.trim() ?: ""
            val password = binding.etPassword.text?.toString()?.trim() ?: ""

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitClient.apiService.validateAuth(
                            mapOf("id" to id, "password" to password)
                        )

                        runOnUiThread {
                            if (response.isSuccessful) {
                                val body = response.body()
                                if (body != null && body.responseCode == 0 && body.data?.isActive == true) {
                                    Toast.makeText(this@LoginActivity, getString(R.string.login_successful),
                                        Toast.LENGTH_SHORT).show()

                                    Util.openActivity(this@LoginActivity, BreedListActivity::class.java,
                                        "breed", null
                                    )
                                } else {
                                    val errorMessage =
                                        body?.message ?: getString(R.string.user_inactive)
                                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(
                                this@LoginActivity, getString(R.string.error_message, e.localizedMessage),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_register -> {
                Util.openActivity(this, RegisterActivity::class.java, null, null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
