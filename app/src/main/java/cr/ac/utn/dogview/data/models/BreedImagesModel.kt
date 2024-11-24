package cr.ac.utn.dogview.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BreedImagesModel(
    val status: String,
    val message: List<String>
)
