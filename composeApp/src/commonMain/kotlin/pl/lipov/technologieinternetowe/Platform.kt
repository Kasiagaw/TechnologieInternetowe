package pl.lipov.technologieinternetowe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform