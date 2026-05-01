package pl.kasiagaw.server

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

// NOWE IMPORTY DLA BAZY DANYCH:
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import pl.kasiagaw.server.model.*

fun main() {
    // NOWE: Łączymy się z bazą danych H2 (w pamięci RAM)
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")

    // NOWE: Tworzymy tabelę i dodajemy przykładowe dane na start
    transaction {
        SchemaUtils.create(ClimateZonesTable)

        ClimateZonesTable.insert {
            it[name] = "Równikow"
            it[description] = "Wysoka temperatura i wilgotność przez cały rok. Brak wyraźnych pór roku."
            it[averageTemp] = 27.0
        }
        ClimateZonesTable.insert {
            it[name] = "Podrównikowy"
            it[description] = "Dwie pory roku: sucha i deszczowa. Wysokie temperatury."
            it[averageTemp] = 25.0
        }
        ClimateZonesTable.insert {
            it[name] = "Zwrotnikowy"
            it[description] = "Bardzo gorące lato, duże dobowe amplitudy temperatur. Często pustynie."
            it[averageTemp] = 23.0
        }
        ClimateZonesTable.insert {
            it[name] = "Podzwrotnikowy"
            it[description] = "Ciepłe, suche lata i łagodne, wilgotne zimy (np. klimat śródziemnomorski)."
            it[averageTemp] = 16.0
        }
        ClimateZonesTable.insert {
            it[name] = "Umiarkowany"
            it[description] = "Wyraźne cztery pory roku. Zmienne warunki pogodowe."
            it[averageTemp] = 9.0
        }
        ClimateZonesTable.insert {
            it[name] = "Okołobiegunowa (Subpolarna)"
            it[description] = "Długie, mroźne zimy i krótkie, chłodne lata. Wieloletnia zmarzlina."
            it[averageTemp] = -5.0
        }
        ClimateZonesTable.insert {
            it[name] = "Biegunowa (Polarna)"
            it[description] = "Zawsze zimno, temperatury rzadko przekraczają 0 stopni. Pustynie lodowe."
            it[averageTemp] = -20.0
        }
    }

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        routing {
            // TWÓJ STARY KOD (zostaje bez zmian!)
            get("/") {
                call.respondText("API działa!")
            }
            get("/hello/{name}") {
                val name = call.parameters["name"] ?: "unknown"
                call.respond(mapOf("message" to "Hello, $name"))
            }

            // NOWY ENDPOINT: Zwraca listę stref z bazy danych
            get("/zones") {
                val zones = transaction {
                    ClimateZonesTable.selectAll().map {
                        ClimateZone(
                            id = it[ClimateZonesTable.id],
                            name = it[ClimateZonesTable.name],
                            description = it[ClimateZonesTable.description],
                            averageTemp = it[ClimateZonesTable.averageTemp]
                        )
                    }
                }
                call.respond(zones)
            }
        }
    }.start(wait = true)
}