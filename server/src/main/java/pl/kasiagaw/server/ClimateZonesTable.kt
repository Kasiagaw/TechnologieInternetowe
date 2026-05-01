package pl.kasiagaw.server.model

import org.jetbrains.exposed.sql.Table

object ClimateZonesTable : Table("climate_zones") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val description = text("description")
    val averageTemp = double("average_temp")

    override val primaryKey = PrimaryKey(id)
}