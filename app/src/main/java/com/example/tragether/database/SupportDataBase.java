/*
package com.example.tragether.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tragether.model.User;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class SupportDataBase extends RoomDatabase {

    private static SupportDataBase instance;

    public abstract UserDao userDao();

    public static synchronized SupportDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SupportDataBase.class, "support_database")
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
*/
