import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.github.rmhavatar.weatherforecast.R
import io.github.rmhavatar.weatherforecast.data.receiver.GpsReceiver
import io.github.rmhavatar.weatherforecast.ui.screen.ForecastViewModel
import io.github.rmhavatar.weatherforecast.ui.screen.footer.Footer
import io.github.rmhavatar.weatherforecast.ui.screen.header.Header
import io.github.rmhavatar.weatherforecast.util.connectivity.base.IConnectivityProvider
import io.github.rmhavatar.weatherforecast.util.connectivity.hasInternet
import io.github.rmhavatar.weatherforecast.util.isLocationEnabled
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ForecastScreen(hostState: SnackbarHostState, viewModel: ForecastViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val connectivityProvider: IConnectivityProvider = IConnectivityProvider.createProvider(context)
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val coroutineScope = rememberCoroutineScope()
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    var isFirstTime by rememberSaveable {
        mutableStateOf(true)
    }
    var lastAllPermissionsGranted by rememberSaveable {
        mutableStateOf(locationPermissions.allPermissionsGranted)
    }
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var gpsIcon by remember {
        mutableStateOf(
            if (isLocationEnabled(locationManager)) {
                R.drawable.baseline_location_on_24
            } else {
                R.drawable.baseline_location_off_24
            }
        )
    }

    val gpsReceiver = GpsReceiver(object : GpsReceiver.LocationCallBack {
        override fun turnedOn() {
            gpsIcon = R.drawable.baseline_location_on_24
        }

        override fun turnedOff() {
            gpsIcon = R.drawable.baseline_location_off_24
        }
    })
    context.registerReceiver(
        gpsReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
    )

    val weatherDataResponseState by viewModel.weatherDataFlow.collectAsStateWithLifecycle()
    Surface {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Header(searchText = searchText) { text ->
                searchText = text
                if (connectivityProvider.getNetworkState().hasInternet()) {
                    viewModel.fetchWeatherDataByCityName(searchText)
                } else {
                    showNoInternetMessage(
                        coroutineScope = coroutineScope,
                        hostState = hostState
                    )
                }

            }
            Body(
                weatherDataResponseState = weatherDataResponseState,
                modifier = Modifier.weight(1f)
            ) { message ->
                showMessage(
                    coroutineScope = coroutineScope,
                    hostState = hostState,
                    message = message
                )
            }
            Footer(gpsIcon = gpsIcon) {
                if (locationPermissions.allPermissionsGranted) {
                    if (!isLocationEnabled(locationManager)) {
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    } else if (connectivityProvider.getNetworkState().hasInternet()) {
                        viewModel.fetchWeatherDataByCoordinates()
                    } else {
                        showNoInternetMessage(coroutineScope, hostState)
                    }
                } else {
                    locationPermissions.launchMultiplePermissionRequest()
                }
            }
        }
    }
    DisposableEffect(key1 = lifecycleOwner) {
        onDispose {
            context.unregisterReceiver(gpsReceiver)
        }
    }

    if (isFirstTime) {
        isFirstTime = false
        if (connectivityProvider.getNetworkState().hasInternet()) {
            viewModel.fetchWeatherData(locationPermissions.allPermissionsGranted)
        }
    } else if (locationPermissions.allPermissionsGranted && locationPermissions.allPermissionsGranted != lastAllPermissionsGranted) {
        if (!isLocationEnabled(locationManager)) {
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else if (connectivityProvider.getNetworkState().hasInternet()) {
            lastAllPermissionsGranted = locationPermissions.allPermissionsGranted
            viewModel.fetchWeatherDataByCoordinates()
        } else {
            showNoInternetMessage(coroutineScope, hostState)
        }
    }
}

private fun showMessage(
    coroutineScope: CoroutineScope,
    hostState: SnackbarHostState,
    message: String
) {
    coroutineScope.launch {
        hostState.showSnackbar(message)
    }
}

private fun showNoInternetMessage(coroutineScope: CoroutineScope, hostState: SnackbarHostState) {
    showMessage(coroutineScope, hostState, "No internet. Check your network connexion")
}