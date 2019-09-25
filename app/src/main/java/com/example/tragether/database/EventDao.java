package com.example.tragether.database;

import androidx.room.*;

import com.example.tragether.model.Event;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);


}
