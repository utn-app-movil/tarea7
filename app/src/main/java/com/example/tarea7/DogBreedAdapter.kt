package com.example.tarea7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DogBreedAdapter(
    private val breedList: List<String>,
    private val onBreedClick: (String) -> Unit
) : RecyclerView.Adapter<DogBreedAdapter.DogBreedViewHolder>() {

    class DogBreedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val breedTextView: TextView = view.findViewById(R.id.breedNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog_breed, parent, false)
        return DogBreedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {
        val breedName = breedList[position]
        holder.breedTextView.text = breedName
        holder.itemView.setOnClickListener { onBreedClick(breedName) }
    }

    override fun getItemCount(): Int = breedList.size
}
