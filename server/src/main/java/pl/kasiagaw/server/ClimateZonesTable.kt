package pl.kasiagaw.server.model

import org.jetbrains.exposed.sql.Table

object ClimateZonesTable : Table("climate_zones") {
    val id = varchar("id", 50) // Zmieniamy na varchar, żeby zgadzało się z Twoim ID ("rownik", "polarny" itd.)
    val name = varchar("name", 100)
    val temperature = varchar("temperature", 200)
    val vegetation = varchar("vegetation", 200)
    val animals = varchar("animals", 200)
    val imageUrl = varchar("image_url", 255)

    override val primaryKey = PrimaryKey(id)
}