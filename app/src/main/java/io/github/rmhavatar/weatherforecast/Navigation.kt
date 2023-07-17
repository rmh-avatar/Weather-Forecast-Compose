package io.github.rmhavatar.weatherforecast

import ForecastScreen
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
            ForecastScreen(hostState = hostState)
        }
    }
}
