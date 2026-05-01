package pl.kasiagaw.technologieinternetowe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.kasiagaw.technologieinternetowe.data.repository.ClimateZonesLocalRepository
import pl.kasiagaw.technologieinternetowe.domain.model.ClimateZone
import pl.kasiagaw.technologieinternetowe.domain.useCase.GetAllZonesUseCase
import pl.kasiagaw.technologieinternetowe.domain.useCase.ToggleZoneFavoriteUseCase

class ClimateZonesViewModel : ViewModel() {

    companion object {
        // Domyślny czas opóźnienia polecany przez Google
        private const val STOP_TIMEOUT_MS = 5_000L
    }

    // Podłączamy nasze Use Case'y
    private val getAllZonesUseCase = GetAllZonesUseCase(
        repository = ClimateZonesLocalRepository()
    )

    private val toggleZoneFavoriteUseCase = ToggleZoneFavoriteUseCase(
        repository = ClimateZonesLocalRepository()
    )

    // Strumień danych, który ekran będzie obserwował
    val zones: StateFlow<List<ClimateZone>> = getAllZonesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
            initialValue = emptyList()
        )

    // Funkcja wywoływana, gdy użytkownik kliknie ikonkę serduszka
    fun handleToggleFavoriteClick(zone: ClimateZone) {
        viewModelScope.launch {
            toggleZoneFavoriteUseCase(zone.id)
        }
    }
}