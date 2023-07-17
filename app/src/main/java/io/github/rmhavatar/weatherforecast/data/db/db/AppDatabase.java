package io.github.rmhavatar.weatherforecast.data.db.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.rmhavatar.weatherforecast.data.db.dao.SearchDao;
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity;
import io.github.rmhavatar.weatherforecast.data.db.type_converter.DateConverter;

@Database(
        entities = {SearchEntity.class},
        version = 1
)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "historic_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract SearchDao searchDao();
}
