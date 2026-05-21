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
import kotlinx.serialization.Serializable

// 1. Definicja tabeli w bazie danych
object ClimateZonesTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val description = text("description")
    val averageTemp = double("average_temp")
    val imageUrl = varchar("image_url", 255)
    override val primaryKey = PrimaryKey(id)
}

// 2. Model danych dla serwera (musi pasować do tego w commonMain!)
@Serializable
data class ClimateZone(
    val id: Int,
    val name: String,
    val description: String,
    val averageTemp: Double,
    val imageUrl: String
)

fun main() {
    // 3. Połączenie z bazą danych H2 (w pamięci)
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")

    // 4. Utworzenie tabeli i dodanie danych
    transaction {
        SchemaUtils.create(ClimateZonesTable)

        // Lista stref. Upewnij się, że nazwy plików ".jpg" zgadzają się z tymi w folderze 'static'!
        val zonesData = listOf(
            Triple("Równikowa", "Gorąco i wilgotno przez cały rok.", 27.0) to "rownik.jpg",
            Triple("Zwrotnikowa", "Gorące lato, pustynie.", 23.0) to "zwrotnikowy.jpg",
            Triple("Podzwrotnikowa", "Ciepłe suche lato, łagodna zima.", 16.0) to "podzwrotnikowy.jpg",
            Triple("Umiarkowana", "Cztery pory roku.", 9.0) to "umiarkowany.jpg",
            Triple("Biegunowa", "Zimno, pustynie lodowe.", -20.0) to "polarny.jpg"
        )

        zonesData.forEach { (info, imageFileName) ->
            ClimateZonesTable.insert {
                it[name] = info.first
                it[description] = info.second
                it[averageTemp] = info.third
                // Używamy Twojego IP! Jeśli zmieniłaś sieć Wi-Fi, trzeba będzie je zaktualizować.
                it[imageUrl] = "http://172.20.10.2:8080/static/$imageFileName"
            }
        }
    }

    // 5. Uruchomienie serwera
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        routing {
            // Ten endpoint zwraca listę stref klimatycznych
            get("/zones") {
                val zones = transaction {
                    ClimateZonesTable.selectAll().map {
                        ClimateZone(
                            id = it[ClimateZonesTable.id],
                            name = it[ClimateZonesTable.name],
                            description = it[ClimateZonesTable.description],
                            averageTemp = it[ClimateZonesTable.averageTemp],
                            imageUrl = it[ClimateZonesTable.imageUrl]
                        )
                    }
                }
                call.respond(zones)
            }

            // Ten wpis "otwiera" folder static, żeby aplikacja mogła pobrać zdjęcia
            staticResources("/static", "static")
        }
    }.start(wait = true)
}