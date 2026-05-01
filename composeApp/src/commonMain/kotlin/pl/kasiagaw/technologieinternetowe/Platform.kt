package pl.kasiagaw.technologieinternetowe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform