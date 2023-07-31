package io.github.rmhavatar.weatherforecast.ui.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity
import io.github.rmhavatar.weatherforecast.data.repository.search_historic.ISearchHistoricRepository
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(searchHistoricRepository: ISearchHistoricRepository) :
    ViewModel() {
    val allSearches: LiveData<List<SearchEntity>>? = searchHistoricRepository.allSearch

}