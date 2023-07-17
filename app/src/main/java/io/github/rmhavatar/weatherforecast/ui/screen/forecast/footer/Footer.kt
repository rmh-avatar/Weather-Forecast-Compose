package io.github.rmhavatar.weatherforecast.ui.screen.forecast.footer

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.rmhavatar.weatherforecast.R

@Composable
fun Footer(
    @DrawableRes gpsIcon: Int,
    onLocationButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.app_name))
        IconButton(onClick = onLocationButtonClick) {
            Icon(
                painter = painterResource(id = gpsIcon),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FooterPrev() {
    Footer(gpsIcon = R.drawable.baseline_location_on_24, onLocationButtonClick = {})
}