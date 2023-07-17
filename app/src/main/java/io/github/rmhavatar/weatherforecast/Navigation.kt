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
        composable(Screen.Forecast.route) { backStackEntry ->
            val searchText: String? =
                backStackEntry.savedStateHandle[SearchScreen.CLICKED_SEARCH_TEXT_KEY]
            ForecastScreen(
                navController,
                hostState = hostState,
                searchTextFromHistorical = searchText
            )
            backStackEntry.savedStateHandle.remove<String>(SearchScreen.CLICKED_SEARCH_TEXT_KEY)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
    }
}
