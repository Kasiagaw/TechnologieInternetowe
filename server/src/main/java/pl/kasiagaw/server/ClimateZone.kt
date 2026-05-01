package pl.kasiagaw.server.model

import kotlinx.serialization.Serializable

@Serializable
data class ClimateZone(
    val id: Int,
    val name: String,        // np. "Zwrotnikowa", "Podbiegunowa"
    val description: String, // krótki opis klimatu
    val averageTemp: Double  // średnia temperatura
)