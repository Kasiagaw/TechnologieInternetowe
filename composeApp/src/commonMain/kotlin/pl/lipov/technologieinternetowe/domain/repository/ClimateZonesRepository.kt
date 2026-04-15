package pl.lipov.technologieinternetowe.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.lipov.technologieinternetowe.domain.model.ClimateZone

interface ClimateZonesRepository {
    fun getAllZones(): Flow<List<ClimateZone>>
    suspend fun toggleZoneFavorite(zoneId: String)
}