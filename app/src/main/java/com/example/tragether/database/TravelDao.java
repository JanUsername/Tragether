package com.example.tragether.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tragether.model.Travel;

import java.util.ArrayList;
import java.util.Date;

@Dao
public interface TravelDao {

   /* @Query("SELECT * FROM user_table")
    ArrayList<Travel> loadTravels();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Travel travel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Travel travel);

    @Delete
    void delete(Travel user);

    @Query("SELECT timestamp FROM user_table WHERE email LIKE :uEmail")
    Date getTimestamp(String uEmail);
*/
}
