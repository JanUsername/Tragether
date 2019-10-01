package com.example.tragether.database;

import androidx.room.*;

import com.example.tragether.model.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Query("SELECT * FROM events_table WHERE organizer LIKE :user ")
    List<Event> loadMyEvents(String user);

    @Query("SELECT * FROM events_table WHERE organizer NOT LIKE :user ")
    List<Event> loadEvents(String user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Event event);

    @Delete
    void delete(Event event);


}
