package pl.kasiagaw.technologieinternetowe.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FavoriteZonesScreen(viewModel: ClimateZonesViewModel) {
    val zonesState = viewModel.zones.collectAsState()

    // Magia filtrowania: wybieramy tylko te strefy, które mają isFavorite == true
    val favoriteZones = zonesState.value.filter { it.isFavorite }

    if (favoriteZones.isEmpty()) {
        // Jeśli nie ma ulubionych, pokazujemy przyjazny komunikat na środku ekranu
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nie masz jeszcze ulubionych stref 🤍", fontSize = 16.sp)
        }
    } else {
        // Jeśli są ulubione, pokazujemy je w takiej samej siatce jak na głównym ekranie
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favoriteZones) { zone ->
                ClimateZoneItem(
                    zone = zone,
                    onToggleFavoriteClick = { viewModel.handleToggleFavoriteClick(it) }
                )
            }
        }
    }
}