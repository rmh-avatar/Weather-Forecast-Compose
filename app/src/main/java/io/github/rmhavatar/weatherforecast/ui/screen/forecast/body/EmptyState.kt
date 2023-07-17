package io.github.rmhavatar.weatherforecast.ui.screen.forecast.body

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.rmhavatar.weatherforecast.R
import io.github.rmhavatar.weatherforecast.ui.theme.WeatherForecastTheme

@Composable
fun EmptyState(text: String, @DrawableRes icon: Int, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(256.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun EmptyStatePrev() {
    WeatherForecastTheme {
        EmptyState("Search", R.drawable.ic_undraw_location_search_re_ttoj)
    }
}