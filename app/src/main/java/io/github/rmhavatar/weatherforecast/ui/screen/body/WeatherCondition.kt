package io.github.rmhavatar.weatherforecast.ui.screen.body

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.rmhavatar.weatherforecast.R
import io.github.rmhavatar.weatherforecast.ui.theme.WeatherForecastTheme

@Composable
fun WeatherCondition(humidity: String, pressure: String, windSpeed: String) {
    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            WeatherConditionItem(
                icon = R.drawable.ic_hygrometer,
                title = stringResource(R.string.humidity_measure, humidity),
                subtitle = stringResource(R.string.humidity)
            )
            WeatherConditionItem(
                icon = R.drawable.ic_pressure,
                title = stringResource(R.string.pressure_measure, pressure),
                subtitle = stringResource(R.string.pressure)
            )
            WeatherConditionItem(
                icon = R.drawable.ic_wind_speed,
                title = stringResource(R.string.wind_speed_measure, windSpeed),
                subtitle = stringResource(R.string.wind_speed)
            )
        }
    }
}

@Composable
fun WeatherConditionItem(@DrawableRes icon: Int, title: String, subtitle: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(painter = painterResource(id = icon), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, textAlign = TextAlign.Center)
        }
        Text(text = subtitle, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun WeatherConditionPrev() {
    WeatherForecastTheme {
        WeatherCondition(humidity = "1", pressure = "1", windSpeed = "1")
    }
}