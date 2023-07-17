package io.github.rmhavatar.weatherforecast.data.db.type_converter;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Date fromTimestamp(Long timestamp) {
        return new Date(timestamp);
    }

    @TypeConverter
    public Long toTimestamp(Date date) {
        return date.getTime();
    }
}
