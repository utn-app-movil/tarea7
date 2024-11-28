package utn.cr.tarea07

import Interface.ApiDog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.launch

class listPets : AppCompatActivity() {
    private lateinit var listPets: ListView
    private lateinit var breedList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_pets)
        listPets = findViewById(R.id.listDogs)
        breedList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breedList)
        listPets.adapter = adapter
        findDo()


       //razas en bloques
        listPets.setOnItemClickListener { _, _, position, _ ->
            val breedName = breedList[position]
            val intent = Intent(this, images_activity::class.java)
            intent.putExtra("nameDogs", breedName)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun findDo() {
        lifecycleScope.launch {
            try {
                val response = ApiDog.api.getBreeds()
                if (response.isSuccessful) {
                    val breeds = response.body()?.message ?: emptyMap()
                    breedList.clear()
                    breedList.addAll(breeds.keys)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@listPets, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@listPets , "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@listPets, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}