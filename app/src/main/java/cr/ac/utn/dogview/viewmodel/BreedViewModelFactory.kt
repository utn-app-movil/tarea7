package cr.ac.utn.dogview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cr.ac.utn.dogview.data.repository.BreedsRepository

/**
 * Factory para instanciar ViewModels relacionados con razas y galería.
 */
class BreedViewModelFactory(private val repository: BreedsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BreedsViewModel::class.java) -> BreedsViewModel(repository) as T
            modelClass.isAssignableFrom(BreedGalleryViewModel::class.java) -> BreedGalleryViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
