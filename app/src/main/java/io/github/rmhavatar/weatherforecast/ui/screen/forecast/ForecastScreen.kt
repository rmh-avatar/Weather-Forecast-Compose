package io.github.rmhavatar.weatherforecast.ui.screen.forecast

import Body
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
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.github.rmhavatar.weatherforecast.R
import io.github.rmhavatar.weatherforecast.Screen
import io.github.rmhavatar.weatherforecast.data.receiver.GpsReceiver
import io.github.rmhavatar.weatherforecast.ui.screen.forecast.footer.Footer
import io.github.rmhavatar.weatherforecast.ui.screen.forecast.header.Header
import io.github.rmhavatar.weatherforecast.util.connectivity.base.IConnectivityProvider
import io.github.rmhavatar.weatherforecast.util.connectivity.hasInternet
import io.github.rmhavatar.weatherforecast.util.isLocationEnabled
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ForecastScreen(
    navController: NavController,
    hostState: SnackbarHostState,
    viewModel: ForecastViewModel = hiltViewModel(),
    searchTextFromHistorical: String? = null
) {
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

    //Used to fetch data once when app is launched
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

    // Receiver to know gps status
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
            Header(
                searchText = searchText,
                onValueChange = { text ->
                    searchText = text
                    if (connectivityProvider.getNetworkState().hasInternet()) {
                        viewModel.fetchWeatherDataByCityName(searchText)
                    } else {
                        showNoInternetMessage(
                            context = context,
                            coroutineScope = coroutineScope,
                            hostState = hostState
                        )
                    }

                },
                onNavigateToSearch = { navController.navigate(Screen.Search.route) })
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
                        showNoInternetMessage(
                            context = context,
                            coroutineScope = coroutineScope,
                            hostState = hostState
                        )
                    }
                } else {
                    locationPermissions.launchMultiplePermissionRequest()
                }
            }
        }
    }

    // Attached to compose lifecycle because when compose leaves the composition its lifecycleOwner
    // is changed and this DisposableEffect when its key is changed calls again its effect but first
    // it must dispose its current effect. Therefore when a compose leaves the composition is called
    // onDispose in DisposableEffect
    DisposableEffect(key1 = lifecycleOwner) {
        onDispose {
            // It needed because it should not be a receiver that is not listen to any f
            context.unregisterReceiver(gpsReceiver)
        }
    }

    if (searchTextFromHistorical?.isNotBlank() == true) {
        viewModel.fetchWeatherDataByCityName(searchTextFromHistorical)
        return
    }

    // If app is launched for first time, fetch weather data from location or search last city.
    // This allows if there is a configuration change the data is not fetched again
    if (isFirstTime) {
        isFirstTime = false
        if (connectivityProvider.getNetworkState().hasInternet()) {
            viewModel.fetchWeatherData(
                locationPermissions.allPermissionsGranted && isLocationEnabled(
                    locationManager
                )
            )
        }
        return
    }

    // If location permission is granted is not first time app is launched, fetch weather data from location
    if (locationPermissions.allPermissionsGranted && locationPermissions.allPermissionsGranted != lastAllPermissionsGranted) {
        if (!isLocationEnabled(locationManager)) {
            // Open change gps setting activity
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else if (connectivityProvider.getNetworkState().hasInternet()) {
            lastAllPermissionsGranted = locationPermissions.allPermissionsGranted
            viewModel.fetchWeatherDataByCoordinates()
        } else {
            showNoInternetMessage(
                context = context,
                coroutineScope = coroutineScope,
                hostState = hostState
            )
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

private fun showNoInternetMessage(
    context: Context,
    coroutineScope: CoroutineScope,
    hostState: SnackbarHostState
) {
    showMessage(
        coroutineScope, hostState,
        context.getString(R.string.no_internet_check_your_network_connexion)
    )
}