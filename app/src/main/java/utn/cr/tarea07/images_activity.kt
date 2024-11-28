package utn.cr.tarea07

import Interface.ApiDog
import adapt.adapIm
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.launch

class images_activity : AppCompatActivity() {
    private lateinit var viewimages: GridView
    private lateinit var listI: ArrayList<String>
    private lateinit var adap: adapIm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_images)
        viewimages = findViewById(R.id.viewIMG)
        listI = java.util.ArrayList()

        val breedName = intent.getStringExtra("nameDogs")
        breedName?.let {
            findIMG(it)
        } ?: run {
            Toast.makeText(this, "No breed selected", Toast.LENGTH_SHORT).show()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun findIMG(breedName: String) {
        lifecycleScope.launch {
            try {
                val response = ApiDog.api.getBreedImages(breedName)
                if (response.isSuccessful) {
                    val images = response.body()?.message ?: emptyList()
                    listI.clear()
                    listI.addAll(images)
                    adap = adapIm(this@images_activity, listI)
                    viewimages.adapter = adap
                } else {
                    Toast.makeText(this@images_activity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@images_activity, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@images_activity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}