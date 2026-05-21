package pl.kasiagaw.server.model

import kotlinx.serialization.Serializable

@Serializable
data class ClimateZone(
    val id: String,          // Zmieniamy na String (bo w tabeli mamy varchar("id", 50))
    val name: String,
    val temperature: String, // Zmieniamy z Double na String, żeby pasowało do bazy
    val vegetation: String,  // Dodajemy pola, których brakowało
    val animals: String,
    val imageUrl: String
)