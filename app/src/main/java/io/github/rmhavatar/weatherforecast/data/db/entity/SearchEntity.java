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
    private final Long id;
    private final String cityName;
    private final Date date;

    public SearchEntity(@NonNull Long id, @NonNull String cityName, @NonNull Date date) {
        this.id = id;
        this.cityName = cityName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public Date getDate() {
        return date;
    }
}
