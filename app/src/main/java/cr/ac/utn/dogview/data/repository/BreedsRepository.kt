package cr.ac.utn.dogview.data.repository

import cr.ac.utn.dogview.data.models.BreedsModel
import cr.ac.utn.dogview.data.models.BreedImagesModel
import cr.ac.utn.dogview.data.network.DogApi
import retrofit2.Response

/**
 * Repositorio encargado de manejar las operaciones relacionadas con las razas de perros.
 */
class BreedsRepository(private val api: DogApi) {

    // Obtiene la lista completa de razas desde la API
    suspend fun getAllBreeds(): Response<BreedsModel> {
        return api.getAllBreeds()
    }

    // Obtiene las imágenes de una raza específica desde la API
    suspend fun getBreedImages(breed: String): Response<BreedImagesModel> {
        return api.getBreedImages(breed)
    }
}
