package io.github.rmhavatar.weatherforecast.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity;

@Dao
public interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchEntity searchEntity);

    @Query("select * from searches order by date desc")
    LiveData<List<SearchEntity>> getAllSearchLiveData();

    @Query("select * from searches order by date desc")
    List<SearchEntity> getAllSearch();
}
