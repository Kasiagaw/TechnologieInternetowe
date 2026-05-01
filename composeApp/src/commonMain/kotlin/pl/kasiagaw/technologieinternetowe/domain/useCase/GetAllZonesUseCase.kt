package pl.kasiagaw.technologieinternetowe.domain.useCase

import kotlinx.coroutines.flow.Flow
import pl.kasiagaw.technologieinternetowe.domain.model.ClimateZone
import pl.kasiagaw.technologieinternetowe.domain.repository.ClimateZonesRepository

class GetAllZonesUseCase(
    private val repository: ClimateZonesRepository
) {
    operator fun invoke(): Flow<List<ClimateZone>> {
        return repository.getAllZones()
    }
}