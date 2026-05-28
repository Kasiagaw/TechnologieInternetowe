package pl.kasiagaw.technologieinternetowe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

        private const val SEARCH_THRESHOLD = 3 // minimalna liczba znaków do wyszukiwania
    }

    // Podłączamy nasze Use Case'y
    private val getAllZonesUseCase = GetAllZonesUseCase(
        repository = ClimateZonesLocalRepository()
    )

    private val toggleZoneFavoriteUseCase = ToggleZoneFavoriteUseCase(
        repository = ClimateZonesLocalRepository()
    )
    // Stan pola wyszukiwania – co aktualnie wpisał użytkownik
    val searchQuery = MutableStateFlow("")


    // Wszystkie strefy (bez filtrowania)
    private val allZones: StateFlow<List<ClimateZone>> = getAllZonesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
            initialValue = emptyList()
        )

    // Przefiltrowane strefy – to co trafia na ekran
    // combine łączy dwa strumienie: listę stref i zapytanie wyszukiwania
    val zones: StateFlow<List<ClimateZone>> = combine(
        allZones,
        searchQuery
    ) { zones, query ->
        // Filtrujemy tylko gdy użytkownik wpisał co najmniej 3 znaki
        if (query.length < SEARCH_THRESHOLD) {
            zones // zwracamy całą listę bez filtrowania
        } else {
            zones.filter { zone ->
                zone.name.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
        initialValue = emptyList()
    )

    // Wywoływane za każdym razem gdy użytkownik coś wpisuje w pole wyszukiwania
    fun handleSearchQueryChange(query: String) {
        searchQuery.value = query
    }



    // Funkcja wywoływana, gdy użytkownik kliknie ikonkę serduszka
    fun handleToggleFavoriteClick(zone: ClimateZone) {
        viewModelScope.launch {
            toggleZoneFavoriteUseCase(zone.id)
        }
    }
}