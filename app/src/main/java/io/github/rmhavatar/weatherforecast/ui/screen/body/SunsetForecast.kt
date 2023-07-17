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
import io.github.rmhavatar.weatherforecast.util.formatTime
import java.util.Date

@Composable
fun SunsetForecast(sunRiseTime: Long, sunSetTime: Long) {
    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            SunsetForecastItem(
                icon = R.drawable.ic_sunrise,
                title = formatTime(Date(sunRiseTime * 1000)),
                subtitle = stringResource(R.string.sunrise)
            )
            SunsetForecastItem(
                icon = R.drawable.ic_sunset,
                title = formatTime(Date(sunSetTime * 1000)),
                subtitle = stringResource(R.string.sunset)
            )
        }
    }
}

@Composable
fun SunsetForecastItem(title: String, @DrawableRes icon: Int, subtitle: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = title, textAlign = TextAlign.Center)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(painter = painterResource(id = icon), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = subtitle, textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun SunsetForecastPrev() {
    WeatherForecastTheme {
        SunsetForecast(200, 1000)
    }
}