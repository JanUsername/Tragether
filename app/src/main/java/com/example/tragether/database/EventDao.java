package com.example.tragether.database;

import androidx.room.*;

import com.example.tragether.model.Event;

import java.util.ArrayList;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);
/*
    @Query("SELECT * FROM events_table")
    ArrayList<Event> loadEvents();
*/
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Event event);

    @Delete
    void delete(Event event);


}
