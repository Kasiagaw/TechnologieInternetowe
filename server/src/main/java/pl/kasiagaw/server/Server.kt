package pl.kasiagaw.server

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.http.content.staticResources
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import pl.kasiagaw.server.model.ClimateZone
import pl.kasiagaw.server.model.ClimateZonesTable

fun main() {
    // 1. Połączenie z bazą danych H2 (w pamięci RAM)
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")

    // 2. Utworzenie tabeli i dodanie danych
    transaction {
        SchemaUtils.create(ClimateZonesTable)

        val zonesData = listOf(
            ClimateZone(
                id = "rownik",
                name = "Strefa Równikowa",
                temperature = "Ok. 25-28°C przez cały rok",
                vegetation = "Wilgotne lasy równikowe (dżungla)",
                animals = "Małpy, jaguary, papugi",
                imageUrl = "http://10.0.2.2:8080/static/rownik.jpg"
            ),
            ClimateZone(
                id = "zwrotnikowy",
                name = "Strefa Zwrotnikowa",
                temperature = "Gorące dni, bardzo zimne noce",
                vegetation = "Pustynie i półpustynie, kaktusy",
                animals = "Wielbłądy, skorpiony, fenki",
                imageUrl = "http://10.0.2.2:8080/static/zwrotnikowy.jpg"
            ),
            ClimateZone(
                id = "podzwrotnikowy",
                name = "Strefa Podzwrotnikowa",
                temperature = "Gorące, suche lata i łagodne zimy",
                vegetation = "Roślinność śródziemnomorska, cytrusy, oliwki",
                animals = "Danieli, muflony, liczne owady i gady",
                imageUrl = "http://10.0.2.2:8080/static/podzwrotnikowy.jpg"
            ),
            ClimateZone(
                id = "umiarkowany",
                name = "Strefa Umiarkowana",
                temperature = "Wyraźne 4 pory roku",
                vegetation = "Lasy liściaste i mieszane",
                animals = "Sarny, dziki, niedźwiedzie, lisy",
                imageUrl = "http://10.0.2.2:8080/static/umiarkowany.jpg"
            ),
            ClimateZone(
                id = "polarny",
                name = "Strefa Polarna",
                temperature = "Bardzo niskie temperatury, mroźne i długie zimy",
                vegetation = "Tundra, mchy, porosty lub pustynie lodowe",
                animals = "Niedźwiedzie polarne, morsy, foki, pingwiny",
                imageUrl = "http://10.0.2.2:8080/static/polarny.jpg"
            )
        )

        zonesData.forEach { zone ->
            ClimateZonesTable.insert {
                it[id] = zone.id
                it[name] = zone.name
                it[temperature] = zone.temperature
                it[vegetation] = zone.vegetation
                it[animals] = zone.animals
                it[imageUrl] = zone.imageUrl
            }
        }
    }

    // 3. Uruchomienie serwera na porcie 8080
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        routing {
            // Endpoint zwracający listę stref jako JSON
            get("/zones") {
                val zones = transaction {
                    ClimateZonesTable.selectAll().map {
                        ClimateZone(
                            id = it[ClimateZonesTable.id],
                            name = it[ClimateZonesTable.name],
                            temperature = it[ClimateZonesTable.temperature],
                            vegetation = it[ClimateZonesTable.vegetation],
                            animals = it[ClimateZonesTable.animals],
                            imageUrl = it[ClimateZonesTable.imageUrl]
                        )
                    }
                }
                call.respond(zones)
            }

            // Serwowanie obrazków statycznych z folderu resources/static
            staticResources("/static", "static")
        }
    }.start(wait = true)
}