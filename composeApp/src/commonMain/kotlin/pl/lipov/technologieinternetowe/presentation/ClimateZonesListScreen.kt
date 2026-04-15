package pl.lipov.technologieinternetowe.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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