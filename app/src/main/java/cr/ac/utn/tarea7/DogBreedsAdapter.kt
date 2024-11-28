import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.tarea7.R

class DogBreedsAdapter(
    private val breeds: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DogBreedsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val breedName: TextView = itemView.findViewById(R.id.ID_Breed_Name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dog_breed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breed = breeds[position]
        holder.breedName.text = breed

        holder.itemView.setOnClickListener {
            onItemClick(breed)
        }
    }

    override fun getItemCount(): Int = breeds.size
}
