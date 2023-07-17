package io.github.rmhavatar.weatherforecast

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.rmhavatar.weatherforecast.ui.screen.forecast.ForecastScreen
import io.github.rmhavatar.weatherforecast.ui.screen.search.SearchScreen

@Composable
fun WeatherForecastNavHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Forecast.route,
        modifier = modifier
    ) {
        composable(Screen.Forecast.route) {
            ForecastScreen(navController, hostState = hostState)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
    }
}
