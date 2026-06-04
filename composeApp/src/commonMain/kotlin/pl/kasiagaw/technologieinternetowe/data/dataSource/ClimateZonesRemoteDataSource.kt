package pl.kasiagaw.technologieinternetowe.data.dataSource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import pl.kasiagaw.technologieinternetowe.domain.model.ClimateZone

class ClimateZonesRemoteDataSource {

    // Klient HTTP Ktor z obsługą JSON
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // bezpieczne na wypadek dodatkowych pól z serwera
            })
        }
    }

    // Pobiera listę stref z serwera - suspend bo to operacja sieciowa
    suspend fun fetchAllZones(): List<ClimateZone> {
        // 10.0.2.2 to specjalny adres - z emulatora Androida wskazuje na localhost komputera
        return client.get("http://10.0.2.2:8080/zones").body()
    }
}