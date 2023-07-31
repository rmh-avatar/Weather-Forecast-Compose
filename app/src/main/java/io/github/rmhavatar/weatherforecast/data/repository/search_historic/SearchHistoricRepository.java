package io.github.rmhavatar.weatherforecast.data.repository.search_historic;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.rmhavatar.weatherforecast.data.db.dao.SearchDao;
import io.github.rmhavatar.weatherforecast.data.db.db.AppDatabase;
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity;

public class SearchHistoricRepository implements ISearchHistoricRepository {
    private final SearchDao searchDao;

    public SearchHistoricRepository(AppDatabase database) {
        searchDao = database.searchDao();
    }

    @Override
    public void insert(SearchEntity searchEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> searchDao.insert(searchEntity));
    }

    @Override
    public LiveData<List<SearchEntity>> getAllSearch() {
        return searchDao.getAllSearchLiveData();
    }
}
