package pl.kasiagaw.technologieinternetowe.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kasiagaw.technologieinternetowe.data.dataSource.ClimateZonesLocalDataSource
import pl.kasiagaw.technologieinternetowe.domain.model.ClimateZone
import pl.kasiagaw.technologieinternetowe.domain.repository.ClimateZonesRepository

class ClimateZonesLocalRepository : ClimateZonesRepository {
    override fun getAllZones(): Flow<List<ClimateZone>> = ClimateZonesLocalDataSource.zones

    override suspend fun toggleZoneFavorite(zoneId: String) {
        ClimateZonesLocalDataSource.toggleZoneFavorite(zoneId)
    }
}