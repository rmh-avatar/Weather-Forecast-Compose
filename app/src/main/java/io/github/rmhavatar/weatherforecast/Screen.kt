package io.github.rmhavatar.weatherforecast

sealed class Screen(
    val route: String,
) {
    object Forecast : Screen(
        route = "sections"
    )
}
