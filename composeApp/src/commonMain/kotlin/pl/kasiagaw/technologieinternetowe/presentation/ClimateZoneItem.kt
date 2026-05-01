package pl.kasiagaw.technologieinternetowe.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import pl.kasiagaw.technologieinternetowe.domain.model.ClimateZone
import technologieinternetowe.composeapp.generated.resources.*

@Composable
fun getZoneImage(zoneId: String) = when(zoneId) {
    "rownik" -> Res.drawable.rownik
    "zwrotnikowy" -> Res.drawable.zwrotnikowy
    "podzwrotnikowy" -> Res.drawable.podzwrotnikowy
    "umiarkowany" -> Res.drawable.umiarkowany
    "polarny" -> Res.drawable.polarny
    else -> Res.drawable.mapa
}

@Composable
fun ClimateZoneItem(
    zone: ClimateZone,
    onToggleFavoriteClick: (ClimateZone) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Używamy "Card", który sam dodaje zaokrąglenia (16.dp) i cień (elevation)
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth() // Karta dopasowuje się do siatki, zamiast narzucać 200.dp
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.background(Color(0xFFF5F5F5)) // Bardzo jasny, nowoczesny szary
        ) {
            Image(
                painter = painterResource(getZoneImage(zone.id)),
                contentDescription = zone.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                // Górny rząd ze strzałką
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = zone.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        maxLines = 2, // Nie pozwalamy tekstowi zająć więcej niż 2 linie
                        overflow = TextOverflow.Ellipsis // Dodaje "..." na końcu, jeśli słowo jest zbyt gigantyczne
                    )
                    Text(
                        text = if (isExpanded) "🔼" else "🔽",
                        modifier = Modifier
                            .clickable { isExpanded = !isExpanded }
                            .padding(start = 8.dp) // Odpycha strzałkę od tekstu
                    )
                }

                // Rozwijane szczegóły
                AnimatedVisibility(visible = isExpanded) {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "🌡️ ${zone.temperature}", fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "🌿 ${zone.vegetation}", fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "🐾 ${zone.animals}", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Serduszko
                Text(
                    text = if (zone.isFavorite) "❤️ Usuń z ulubionych" else "🤍 Dodaj do ulubionych",
                    modifier = Modifier.clickable { onToggleFavoriteClick(zone) },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}