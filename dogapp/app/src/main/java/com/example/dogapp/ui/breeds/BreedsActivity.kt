package com.example.dogapp.ui.breeds

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.R
import com.example.dogapp.databinding.ActivityBreedsBinding
import com.example.dogapp.databinding.ItemBreedBinding
import com.example.dogapp.ui.gallery.GalleryActivity

class BreedsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBreedsBinding
    private val viewModel: BreedsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Observa las razas desde el ViewModel
        viewModel.breeds.observe(this, Observer { breeds ->
            if (breeds != null) {
                (binding.rvBreeds.adapter as BreedsAdapter).submitList(breeds.keys.toList())
            } else {
                Toast.makeText(this, "Error al cargar las razas", Toast.LENGTH_SHORT).show()
            }
        })

        // Llama al ViewModel para obtener las razas
        viewModel.getBreeds()
    }

    private fun setupRecyclerView() {
        binding.rvBreeds.layoutManager = LinearLayoutManager(this)
        binding.rvBreeds.adapter = BreedsAdapter { breed ->
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("breed", breed)
            startActivity(intent)
        }
    }
}

// Adapter para el RecyclerView
class BreedsAdapter(private val onBreedClick: (String) -> Unit) :
    RecyclerView.Adapter<BreedsAdapter.BreedViewHolder>() {

    private var breedList: List<String> = emptyList()

    fun submitList(breeds: List<String>) {
        breedList = breeds
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(breedList[position])
    }

    override fun getItemCount(): Int = breedList.size

    inner class BreedViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: String) {
            binding.tvBreedName.text = breed
            binding.root.setOnClickListener { onBreedClick(breed) }
        }
    }
}
