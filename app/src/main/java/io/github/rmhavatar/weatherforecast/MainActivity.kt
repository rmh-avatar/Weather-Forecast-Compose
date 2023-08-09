package io.github.rmhavatar.weatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.rmhavatar.weatherforecast.ui.theme.WeatherForecastTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                val hostState = SnackbarHostState()
                Scaffold(snackbarHost = { SnackbarHost(hostState = hostState) }) {
                    WeatherForecastNavHost(
                        hostState = hostState,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}