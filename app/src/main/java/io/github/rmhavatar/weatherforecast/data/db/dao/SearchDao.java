package io.github.rmhavatar.weatherforecast.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.util.ArrayList;

import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity;


public interface SearchDao {
    void insert(SearchEntity searchEntity);

    @Query("select * from searches order by date desc")
    LiveData<ArrayList<SearchEntity>> getAllSearch();
}
