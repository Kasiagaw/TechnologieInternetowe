package pl.kasiagaw.technologieinternetowe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.imageBitmapDecoder
import io.kamel.image.config.svgDecoder
import io.kamel.image.config.imageVectorDecoder
import io.kamel.image.config.LocalKamelConfig
import io.ktor.client.engine.okhttp.OkHttp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Inicjalizacja konfiguracji Kamel (tutaj "wpina się" silnik HTTP)
        val kamelConfig = KamelConfig {
            takeFrom(KamelConfig.Default)
        }

        setContent {
            // Przekazujemy App, jeśli Twoja funkcja App() przyjmuje parametry,
            // jeśli nie, zostaw jak jest.
            CompositionLocalProvider(LocalKamelConfig provides kamelConfig) {
                App()
            }
        }
    }
}