package cr.ac.utn.dogview.ui.register

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.dogview.R
import cr.ac.utn.dogview.data.models.RegisterRequest
import cr.ac.utn.dogview.data.network.ApiClient
import cr.ac.utn.dogview.data.repository.AuthRepository
import cr.ac.utn.dogview.ui.login.LoginActivity
import cr.ac.utn.dogview.utils.Util
import cr.ac.utn.dogview.viewmodel.AuthViewModelFactory
import cr.ac.utn.dogview.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var idField: EditText
    private lateinit var nameField: EditText
    private lateinit var lastNameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var registerButton: Button
    private lateinit var progressBar: ProgressBar

    private val registerViewModel: RegisterViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(ApiClient.authApi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar vistas
        idField = findViewById(R.id.idField)
        nameField = findViewById(R.id.nameField)
        lastNameField = findViewById(R.id.lastNameField)
        passwordField = findViewById(R.id.passwordField)
        confirmPasswordField = findViewById(R.id.confirmPasswordField)
        registerButton = findViewById(R.id.registerButton)
        progressBar = findViewById(R.id.progressBar)

        registerButton.setOnClickListener {
            val id = idField.text.toString()
            val name = nameField.text.toString()
            val lastName = lastNameField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else if (id.isNotEmpty() && name.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty()) {
                performRegister(
                    RegisterRequest(
                        id = id,
                        name = name,
                        lastName = lastName,
                        password = password,
                        isActive = true,
                        isTemporary = true
                    )
                )
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        observeViewModel()
    }

    private fun performRegister(request: RegisterRequest) {
        progressBar.visibility = View.VISIBLE
        registerViewModel.register(request)
    }

    private fun observeViewModel() {
        registerViewModel.registerResult.observe(this) { response ->
            progressBar.visibility = View.GONE
            if (response.isSuccessful) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                // Redirigir a la actividad de login
                Util.openActivity(this, LoginActivity::class.java)
            } else {
                Toast.makeText(this, response.body()?.message ?: "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.errorMessage.observe(this) { error ->
            progressBar.visibility = View.GONE
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnuLogin -> {
                Util.openActivity(this, LoginActivity::class.java)
                true
            }
            R.id.mnuRegister -> {
                Util.openActivity(this, RegisterActivity::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
