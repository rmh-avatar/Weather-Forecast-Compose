package io.github.rmhavatar.weatherforecast.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.rmhavatar.weatherforecast.R
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity
import io.github.rmhavatar.weatherforecast.ui.screen.forecast.body.EmptyState
import io.github.rmhavatar.weatherforecast.ui.theme.WeatherForecastTheme
import java.util.Date

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val list: List<SearchEntity> by viewModel.allSearches!!.observeAsState(emptyList())
    Surface {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.height(56.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
                Text(text = stringResource(id = R.string.historical))
            }
            if (list.isEmpty()) {
                EmptyState(
                    text = stringResource(R.string.no_historical),
                    icon = R.drawable.ic_undraw_empty_re_opql__1_,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(list) { item ->
                        ListItemHolder(text = item.cityName, onSearch = {
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                SearchScreen.CLICKED_SEARCH_TEXT_KEY,
                                item.cityName
                            )
                            navController.popBackStack()
                        })
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun ListItemHolder(text: String, onSearch: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text, modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        IconButton(onClick = { onSearch(text) }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPrev() {
    val list = listOf(SearchEntity(1, "Atlanta", Date()), SearchEntity(1, "Lawrenceville", Date()))
    WeatherForecastTheme {
        Surface {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.height(56.dp)
                ) {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                    Text(text = stringResource(id = R.string.historical))
                }
                if (list.isEmpty()) {
                    EmptyState(
                        text = stringResource(R.string.no_historical),
                        icon = R.drawable.ic_undraw_empty_re_opql__1_,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(list) { item ->
                            ListItemHolder(text = item.cityName, onSearch = { })
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

object SearchScreen {
    const val CLICKED_SEARCH_TEXT_KEY = "CLICKED_SEARCH_TEXT_KEY"
}