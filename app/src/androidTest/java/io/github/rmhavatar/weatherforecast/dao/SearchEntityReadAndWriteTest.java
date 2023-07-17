package io.github.rmhavatar.weatherforecast.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import io.github.rmhavatar.weatherforecast.data.db.dao.SearchDao;
import io.github.rmhavatar.weatherforecast.data.db.db.AppDatabase;
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity;

@RunWith(AndroidJUnit4.class)
public class SearchEntityReadAndWriteTest extends TestCase {
    private SearchDao searchDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        searchDao = db.searchDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeSearchAndReadInList() throws Exception {
        SearchEntity search = new SearchEntity(1L, "City", new Date());
        searchDao.insert(search);
        List<SearchEntity> byName = searchDao.getAllSearch();
        assertThat(byName.get(0).getSearchId(), equalTo(1L));
        assertThat(byName.get(0).getCityName(), equalTo("City"));
    }
}