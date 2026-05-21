plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    id("application")
    alias(libs.plugins.serialization)
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:3.4.1")
    implementation("io.ktor:ktor-server-netty-jvm:3.4.1")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:3.4.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:3.4.1")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("com.h2database:h2:2.2.224")

    // Biblioteki do bazy danych (zgodnie z wykładem)
    implementation("org.jetbrains.exposed:exposed-core:0.45.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")
    implementation("com.h2database:h2:2.2.224") // Baza danych w pamięci RAM
}

application {
    mainClass.set("pl.kasiagaw.server.ServerKt")
}