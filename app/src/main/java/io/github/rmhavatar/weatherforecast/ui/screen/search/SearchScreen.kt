package io.github.rmhavatar.weatherforecast.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import io.github.rmhavatar.weatherforecast.util.formatDateTime
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val list: List<SearchEntity> by viewModel.allSearches!!.observeAsState(emptyList())
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(id = R.string.historical),
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }) {
        Surface(modifier = Modifier.padding(it)) {
            if (list.isEmpty()) {
                EmptyState(
                    text = stringResource(R.string.no_historical),
                    icon = R.drawable.ic_undraw_empty_re_opql__1_,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(list) { item ->
                        ListItemHolder(text = item.cityName, date = item.date, onSearch = {
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
fun ListItemHolder(text: String, date: Date, onSearch: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
            Text(
                text = text, modifier = Modifier
                    .padding(start = 8.dp)
            )
            Text(
                text = formatDateTime(date = date), modifier = Modifier
                    .padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        IconButton(onClick = { onSearch(text) }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchScreenPrev() {
    val list = listOf(SearchEntity(1, "Atlanta", Date()), SearchEntity(1, "Lawrenceville", Date()))
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.historical))
        }, navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }) {
        Surface(modifier = Modifier.padding(it)) {
            if (list.isEmpty()) {
                EmptyState(
                    text = stringResource(R.string.no_historical),
                    icon = R.drawable.ic_undraw_empty_re_opql__1_,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(list) { item ->
                        ListItemHolder(text = item.cityName, date = item.date, onSearch = {})
                        Divider()
                    }
                }
            }
        }
    }
}

object SearchScreen {
    const val CLICKED_SEARCH_TEXT_KEY = "CLICKED_SEARCH_TEXT_KEY"
}