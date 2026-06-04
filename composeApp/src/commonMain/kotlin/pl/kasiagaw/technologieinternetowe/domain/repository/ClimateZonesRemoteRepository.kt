package pl.kasiagaw.technologieinternetowe.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kasiagaw.technologieinternetowe.data.dataSource.ClimateZonesRemoteDataSource
import pl.kasiagaw.technologieinternetowe.domain.model.ClimateZone
import pl.kasiagaw.technologieinternetowe.domain.repository.ClimateZonesRepository

class ClimateZonesRemoteRepository : ClimateZonesRepository {

    private val remoteDataSource = ClimateZonesRemoteDataSource()

    // Stan listy stref trzymamy lokalnie w StateFlow
    // (dane przychodzą z serwera, ale isFavorite zarządzamy sami)
    private val _zones = MutableStateFlow<List<ClimateZone>>(emptyList())

    // Inicjalizacja - pobieramy dane z serwera przy starcie
    init {
        // Uwaga: init nie jest suspend, więc używamy runBlocking tylko tutaj
        // W prawdziwej aplikacji lepiej wywoływać refresh() z ViewModelu
    }

    override fun getAllZones(): Flow<List<ClimateZone>> = _zones.asStateFlow()

    // Pobiera świeże dane z serwera i aktualizuje StateFlow
    suspend fun refresh() {
        try {
            val zonesFromServer = remoteDataSource.fetchAllZones()
            val currentFavorites = _zones.value.filter { it.isFavorite }.map { it.id }.toSet()
            _zones.value = zonesFromServer.map { zone ->
                zone.copy(isFavorite = zone.id in currentFavorites)
            }
        } catch (e: Exception) {
            // Serwer niedostępny - aplikacja działa dalej z pustą listą
            println("Błąd połączenia z serwerem: ${e.message}")
        }
    }

    override suspend fun toggleZoneFavorite(zoneId: String) {
        _zones.update { list ->
            list.map { zone ->
                if (zone.id == zoneId) zone.copy(isFavorite = !zone.isFavorite)
                else zone
            }
        }
    }
}