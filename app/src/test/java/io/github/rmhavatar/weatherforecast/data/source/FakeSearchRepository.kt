package io.github.rmhavatar.weatherforecast.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity
import io.github.rmhavatar.weatherforecast.data.repository.search_historic.ISearchHistoricRepository

class FakeSearchRepository : ISearchHistoricRepository {
    private val searchList: MutableList<SearchEntity> = mutableListOf()
    private val listLiveData: MutableLiveData<MutableList<SearchEntity>> = MutableLiveData()

    override fun insert(searchEntity: SearchEntity) {
        searchList.add(searchEntity)
        listLiveData.value = searchList
    }

    override fun getAllSearch(): LiveData<MutableList<SearchEntity>> {
        return listLiveData
    }
}