package com.example.tragether.database;

import androidx.room.*;

import com.example.tragether.model.Travel;

import java.util.ArrayList;

@Dao
public interface TravelDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insert(Travel travel);
/*
   @Query("SELECT * FROM travels_table")
   ArrayList<Travel> loadTravels();
*/
   @Update(onConflict = OnConflictStrategy.REPLACE)
   void update(Travel travel);

   @Delete
   void delete(Travel travel);

}
