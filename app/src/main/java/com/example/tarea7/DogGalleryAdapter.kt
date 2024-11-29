package com.example.tarea7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DogGalleryAdapter(private val images: List<String>) :
    RecyclerView.Adapter<DogGalleryAdapter.DogGalleryViewHolder>() {

    class DogGalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogGalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog_image, parent, false)
        return DogGalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogGalleryViewHolder, position: Int) {
        val imageUrl = images[position]
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size
}