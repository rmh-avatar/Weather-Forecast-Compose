package io.github.rmhavatar.weatherforecast.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Search history input
 */
@Entity(tableName = "searches")
public class SearchEntity {
    @PrimaryKey(autoGenerate = true)
    private final Long searchId;
    private final String cityName;
    private final Date date;

    public SearchEntity(@NonNull Long searchId, @NonNull String cityName, @NonNull Date date) {
        this.searchId = searchId;
        this.cityName = cityName;
        this.date = date;
    }

    public Long getSearchId() {
        return searchId;
    }

    public String getCityName() {
        return cityName;
    }

    public Date getDate() {
        return date;
    }
}
