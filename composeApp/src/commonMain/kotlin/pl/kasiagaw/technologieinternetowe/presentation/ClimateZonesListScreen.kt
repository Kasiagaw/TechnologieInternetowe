package pl.kasiagaw.technologieinternetowe.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClimateZonesListScreen(
    modifier: Modifier = Modifier,
    viewModel: ClimateZonesViewModel
) {
    val zonesState = viewModel.zones.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()

    Column(modifier = modifier) {
        // Pole wyszukiwania
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { viewModel.handleSearchQueryChange(it) },
            label = { Text("Szukaj strefy...") },
            placeholder = { Text("Wpisz min. 3 znaki") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )


        // Siatka stref (przefiltrowana lub pełna)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(zonesState.value) { zone ->
                ClimateZoneItem(
                    zone = zone,
                    onToggleFavoriteClick = { viewModel.handleToggleFavoriteClick(it) }
                )
            }
        }
    }
}