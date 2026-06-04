package pl.kasiagaw.technologieinternetowe.domain.model
import kotlinx.serialization.Serializable

// @Serializable potrzebne do parsowania JSON z serwera
@Serializable
data class ClimateZone(
    val id: String,
    val name: String,
    val temperature: String,
    val vegetation: String,
    val animals: String,
    val isFavorite: Boolean = false,
    val imageUrl: String = ""
)