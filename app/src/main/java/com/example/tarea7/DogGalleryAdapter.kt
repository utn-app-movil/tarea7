package com.example.tarea7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DogGalleryAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<DogGalleryAdapter.DogGalleryViewHolder>() {

    class DogGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dogImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogGalleryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog_image, parent, false)
        return DogGalleryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DogGalleryViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.dogImageView)
    }

    override fun getItemCount(): Int = imageUrls.size
}
