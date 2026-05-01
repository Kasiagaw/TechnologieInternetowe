package pl.kasiagaw.technologieinternetowe.domain.useCase

import pl.kasiagaw.technologieinternetowe.domain.repository.ClimateZonesRepository

class ToggleZoneFavoriteUseCase(
    private val repository: ClimateZonesRepository
) {
    suspend operator fun invoke(zoneId: String) {
        repository.toggleZoneFavorite(zoneId)
    }
}