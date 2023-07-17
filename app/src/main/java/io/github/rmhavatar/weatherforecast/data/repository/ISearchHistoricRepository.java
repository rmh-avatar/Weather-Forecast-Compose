package io.github.rmhavatar.weatherforecast.data.repository;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity;

public interface ISearchHistoricRepository {
    void insert(SearchEntity searchEntity);

    LiveData<ArrayList<SearchEntity>> getAllSearch();
}
