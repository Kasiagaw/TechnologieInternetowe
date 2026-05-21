package pl.kasiagaw.technologieinternetowe.domain.model

data class ClimateZone(
    val id: String,
    val name: String,
    val temperature: String,
    val vegetation: String,
    val animals: String,
    val isFavorite: Boolean = false,
    val imageUrl: String = ""
)