package io.github.rmhavatar.weatherforecast.search_historic

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity
import io.github.rmhavatar.weatherforecast.data.source.FakeSearchRepository
import io.github.rmhavatar.weatherforecast.ui.screen.search.SearchViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class SearchViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchRepository: FakeSearchRepository

    @Before
    fun setUp() {
        searchRepository = FakeSearchRepository()
        searchViewModel = SearchViewModel(searchRepository)
    }

    @Test
    fun getAllSearches_check_has_one_item() {
        searchRepository.insert(SearchEntity(1, "Atlanta", Date()))
        val searches = searchViewModel.allSearches?.getOrAwaitValue()
        assertEquals(1, searches?.size)
    }

    @Test
    fun getAllSearches_check_has_two_item() {
        searchRepository.insert(SearchEntity(1, "Atlanta", Date()))
        searchRepository.insert(SearchEntity(2, "Boston", Date()))
        val searches = searchViewModel.allSearches?.getOrAwaitValue()
        assertEquals(2, searches?.size)
    }

    @Test
    fun getAllSearches_check_first_item_is_right() {
        searchRepository.insert(SearchEntity(1, "Atlanta", Date()))
        searchRepository.insert(SearchEntity(2, "Boston", Date()))
        val searches = searchViewModel.allSearches?.getOrAwaitValue()
        assertEquals("Atlanta", searches!![0].cityName)
    }
}