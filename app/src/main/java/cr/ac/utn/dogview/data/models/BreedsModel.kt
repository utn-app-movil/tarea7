package cr.ac.utn.dogview.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BreedsModel(
    val status: String,
    val message: Map<String, List<String>>
)
