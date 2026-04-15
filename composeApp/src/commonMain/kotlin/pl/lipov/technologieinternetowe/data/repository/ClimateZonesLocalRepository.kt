package pl.lipov.technologieinternetowe.data.repository

import kotlinx.coroutines.flow.Flow
import pl.lipov.technologieinternetowe.data.dataSource.ClimateZonesLocalDataSource
import pl.lipov.technologieinternetowe.domain.model.ClimateZone
import pl.lipov.technologieinternetowe.domain.repository.ClimateZonesRepository

class ClimateZonesLocalRepository : ClimateZonesRepository {
    override fun getAllZones(): Flow<List<ClimateZone>> = ClimateZonesLocalDataSource.zones

    override suspend fun toggleZoneFavorite(zoneId: String) {
        ClimateZonesLocalDataSource.toggleZoneFavorite(zoneId)
    }
}