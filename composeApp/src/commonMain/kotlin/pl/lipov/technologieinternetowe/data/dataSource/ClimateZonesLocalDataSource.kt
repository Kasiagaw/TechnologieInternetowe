package pl.lipov.technologieinternetowe.data.dataSource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.lipov.technologieinternetowe.domain.model.ClimateZone

object ClimateZonesLocalDataSource {

    // lista stref klimatycznych [cite: 332, 333]
    private val _zones = MutableStateFlow(
        listOf(
            ClimateZone(
                id = "rownik",
                name = "Strefa Równikowa",
                temperature = "Ok. 25-28°C przez cały rok",
                vegetation = "Wilgotne lasy równikowe (dżungla)",
                animals = "Małpy, jaguary, papugi"
            ),
            ClimateZone(
                id = "zwrotnikowy",
                name = "Strefa Zwrotnikowa",
                temperature = "Gorące dni, bardzo zimne noce",
                vegetation = "Pustynie i półpustynie, kaktusy",
                animals = "Wielbłądy, skorpiony, fenki"
            ),

            ClimateZone(
                id = "podzwrotnikowy",
                name = "Strefa Podzwrotnikowa",
                temperature = "Gorące, suche lata i łagodne zimy",
                vegetation = "Roślinność śródziemnomorska, cytrusy, oliwki",
                animals = "Danieli, muflony, liczne owady i gady"
            ),
            ClimateZone(
                id = "umiarkowany",
                name = "Strefa Umiarkowana",
                temperature = "Wyraźne 4 pory roku",
                vegetation = "Lasy liściaste i mieszane",
                animals = "Sarny, dziki, niedźwiedzie, lisy"
            ),
            ClimateZone(
                id = "polarny",
                name = "Strefa Polarna",
                temperature = "Bardzo niskie temperatury, mroźne i długie zimy",
                vegetation = "Tundra, mchy, porosty lub pustynie lodowe",
                animals = "Niedźwiedzie polarne, morsy, foki, pingwiny"
            )
        )
    )

    // Udostępnienie listy stref dla ViewModelu [cite: 347]
    val zones: StateFlow<List<ClimateZone>> = _zones.asStateFlow()

    // Funkcja do oznaczania strefy jako "Ulubiona" [cite: 348, 351]
    fun toggleZoneFavorite(zoneId: String) {
        _zones.update { zonesList ->
            zonesList.map { zone ->
                if (zone.id == zoneId) {
                    zone.copy(isFavorite = !zone.isFavorite) // [cite: 354]
                } else {
                    zone
                }
            }
        }
    }
}