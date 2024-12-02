package com.example.dogapp.ui.gallery

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dogapp.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val breed = intent.getStringExtra("breed") ?: ""
        setupRecyclerView()

        viewModel.images.observe(this, Observer { images ->
            if (images != null) {
                (binding.rvGallery.adapter as GalleryAdapter).submitList(images)
            } else {
                Toast.makeText(this, "Error al cargar las imágenes", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getBreedImages(breed)
    }

    private fun setupRecyclerView() {
        binding.rvGallery.layoutManager = GridLayoutManager(this, 3) // 3 columnas
        binding.rvGallery.adapter = GalleryAdapter()
    }
}
