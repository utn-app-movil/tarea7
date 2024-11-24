package com.example.tarea7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DogBreedAdapter(
    private val breeds: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DogBreedAdapter.DogBreedViewHolder>() {

    class DogBreedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val breedNameTextView: TextView = view.findViewById(R.id.breedNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog_breed, parent, false)
        return DogBreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {
        val breed = breeds[position]
        holder.breedNameTextView.text = breed
        holder.itemView.setOnClickListener { onItemClick(breed) }
    }

    override fun getItemCount(): Int = breeds.size
}