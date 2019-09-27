
package com.example.tragether.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tragether.model.Event;
import com.example.tragether.model.Travel;
import com.example.tragether.model.User;


@Database(entities = {User.class, Travel.class, Event.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SupportDataBase extends RoomDatabase {

    private static SupportDataBase instance;
    private static final String DB = "support_database";
    public abstract UserDao userDao();
    public abstract TravelDao travelDao();
    public abstract EventDao eventDao();

    public static SupportDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SupportDataBase.class, DB)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("db", "I've been created");
        }
    };

}

