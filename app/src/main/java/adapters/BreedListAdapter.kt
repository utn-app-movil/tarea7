package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.investigacion02.databinding.ItemBreedBinding

class BreedListAdapter(
    private val breeds: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<BreedListAdapter.BreedViewHolder>() {

    class BreedViewHolder(private val binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: String, onClick: (String) -> Unit) {
            binding.tvBreedName.text = breed.replaceFirstChar { it.uppercase() }
            binding.root.setOnClickListener { onClick(breed) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(breeds[position], onItemClick)
    }

    override fun getItemCount(): Int = breeds.size
}
