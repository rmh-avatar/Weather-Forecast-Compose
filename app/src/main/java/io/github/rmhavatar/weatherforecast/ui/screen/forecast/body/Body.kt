import android.content.res.Configuration
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.rmhavatar.weatherforecast.R
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import io.github.rmhavatar.weatherforecast.ui.screen.forecast.body.EmptyState
import io.github.rmhavatar.weatherforecast.ui.screen.forecast.body.WeatherCondition
import io.github.rmhavatar.weatherforecast.util.formatDateTime
import io.github.rmhavatar.weatherforecast.util.getWeatherConditionUrl
import java.util.Date


@Composable
fun Body(
    weatherDataResponseState: ResponseState<WeatherResponseData>?,
    modifier: Modifier = Modifier,
    onShowMessage: (String) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        when (weatherDataResponseState) {
            is ResponseState.Error, null -> {
                LocalEmptyState(Modifier.weight(1f))
                if (weatherDataResponseState != null && weatherDataResponseState.message?.isNotBlank() == true) {
                    onShowMessage(weatherDataResponseState.message)
                }
            }

            is ResponseState.Loading -> {
                LoadingShimmer()
            }

            is ResponseState.Success -> {
                if (weatherDataResponseState.data == null) {
                    LocalEmptyState(Modifier.weight(1f))
                } else {
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            BodyHorizontal(data = weatherDataResponseState.data)
                        }

                        else -> {
                            BodyVertical(data = weatherDataResponseState.data)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BodyVertical(data: WeatherResponseData) {
    with(data) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = formatDateTime(Date(forecastTime * 1000)),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            if (weatherCondition.isNotEmpty()) {
                AsyncImage(
                    model = getWeatherConditionUrl(
                        weatherCondition[0].icon
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(164.dp)
                )
                Text(
                    text = weatherCondition[0].condition,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = weatherCondition[0].description.replaceFirstChar { character -> character.uppercase() },
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            TemperatureText(temperature = temperature.temperature.toInt())
            Spacer(modifier = Modifier.height(32.dp))
            WeatherCondition(
                humidity = temperature.humidity.toString(),
                pressure = temperature.pressure.toInt().toString(),
                windSpeed = wind.speed.toString()
            )
            Spacer(modifier = Modifier.height(16.dp))
            SunsetForecast(
                sunRiseTime = sunBehavior.sunrise,
                sunSetTime = sunBehavior.sunset
            )
        }
    }
}

@Composable
fun BodyHorizontal(data: WeatherResponseData) {
    with(data) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = cityName,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = formatDateTime(Date(forecastTime * 1000)),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                if (weatherCondition.isNotEmpty()) {
                    AsyncImage(
                        model = getWeatherConditionUrl(
                            weatherCondition[0].icon
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 164.dp, minHeight = 164.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.weight(1f)
                ) {
                    if (weatherCondition.isNotEmpty()) {
                        Text(
                            text = weatherCondition[0].condition,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = weatherCondition[0].description.replaceFirstChar { character -> character.uppercase() },
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    TemperatureText(temperature = temperature.temperature.toInt())
                }
            }
            WeatherCondition(
                humidity = temperature.humidity.toString(),
                pressure = temperature.pressure.toInt().toString(),
                windSpeed = wind.speed.toString()
            )
            Spacer(modifier = Modifier.height(16.dp))
            SunsetForecast(
                sunRiseTime = sunBehavior.sunrise,
                sunSetTime = sunBehavior.sunset
            )
        }
    }
}

@Composable
fun LocalEmptyState(modifier: Modifier = Modifier) {
    EmptyState(
        text = stringResource(R.string.search_your_city),
        icon = R.drawable.ic_undraw_location_search_re_ttoj,
        modifier = modifier
    )
}

@Composable
fun LoadingShimmer(brush: Brush = shimmerBrush()) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth(fraction = 0.4f)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(fraction = 0.4f)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .size(128.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(brush)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(brush)
            )
        }
    }
}

@Composable
fun shimmerBrush(targetValue: Float = 1000f): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(800), repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
}

@Composable
fun TemperatureText(temperature: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = buildAnnotatedString {
                append(temperature.toString())
                withStyle(
                    SpanStyle(
                        baselineShift = BaselineShift.Superscript,
                    )
                ) {
                    append(stringResource(R.string.degree_char))
                }
                append(" ")
                append(stringResource(R.string.fahrenheit_unit))
            },
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}