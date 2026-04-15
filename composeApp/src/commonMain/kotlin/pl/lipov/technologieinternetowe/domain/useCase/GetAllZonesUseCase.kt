package pl.lipov.technologieinternetowe.domain.useCase

import kotlinx.coroutines.flow.Flow
import pl.lipov.technologieinternetowe.domain.model.ClimateZone
import pl.lipov.technologieinternetowe.domain.repository.ClimateZonesRepository

class GetAllZonesUseCase(
    private val repository: ClimateZonesRepository
) {
    operator fun invoke(): Flow<List<ClimateZone>> {
        return repository.getAllZones()
    }
}