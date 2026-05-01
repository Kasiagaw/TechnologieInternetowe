package pl.kasiagaw.technologieinternetowe.presentation

// Używamy emotikon jako "ikon", aby uniknąć problemów z bibliotekami graficznymi
sealed class NavigationItem(val route: String, val title: String, val iconText: String) {
    object Home : NavigationItem("home", "Strefy", "🌍")
    object Favorites : NavigationItem("favorites", "Ulubione", "❤️")
}