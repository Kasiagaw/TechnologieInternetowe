package pl.lipov.technologieinternetowe.domain.useCase

import pl.lipov.technologieinternetowe.domain.repository.ClimateZonesRepository

class ToggleZoneFavoriteUseCase(
    private val repository: ClimateZonesRepository
) {
    suspend operator fun invoke(zoneId: String) {
        repository.toggleZoneFavorite(zoneId)
    }
}