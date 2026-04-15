package pl.lipov.technologieinternetowe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource

// Bardzo ważny import! Dzięki niemu App.kt widzi Twoje nowe ekrany i nawigację.
import pl.lipov.technologieinternetowe.presentation.*

import technologieinternetowe.composeapp.generated.resources.Res
import technologieinternetowe.composeapp.generated.resources.mapa

@Composable
fun App() {
    val viewModel: ClimateZonesViewModel = viewModel { ClimateZonesViewModel() }

    // 1. Zmienna pamiętająca, w co kliknęłaś na dole (zaczynamy od "Home", czyli wszystkich stref)
    var selectedItem by remember { mutableStateOf<NavigationItem>(NavigationItem.Home) }

    // 2. Lista naszych dwóch przycisków na pasek
    val items = listOf(NavigationItem.Home, NavigationItem.Favorites)

    MaterialTheme {
        Scaffold(
            bottomBar = {
                // 3. Pasek nawigacji na dole ekranu
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Text(text = item.iconText, fontSize = 24.sp) }, // Wyświetla 🌍 lub ❤️
                            label = { Text(item.title) }, // Wyświetla "Strefy" lub "Ulubione"
                            selected = selectedItem == item, // Podświetla aktywny przycisk
                            onClick = { selectedItem = item } // Zmienia ekran po kliknięciu
                        )
                    }
                }
            }
        ) { padding ->
            // Główny obszar ekranu (zostawia miejsce na pasek dzięki "padding")
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {

                // WARSTWA 1: TŁO (Mapa)
                Image(
                    painter = painterResource(Res.drawable.mapa),
                    contentDescription = "Tło mapy świata",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // WARSTWA 2: PRZEŁĄCZANIE EKRANÓW
                when (selectedItem) {
                    is NavigationItem.Home -> {
                        // Jeśli wciśnięto 🌍
                        ClimateZonesListScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = viewModel
                        )
                    }
                    is NavigationItem.Favorites -> {
                        // Jeśli wciśnięto ❤️
                        FavoriteZonesScreen(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}