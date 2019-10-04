package com.example.tragether.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.tragether.model.Travel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TravelDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insert(Travel travel);

   @Query("SELECT * FROM travels_table")
   public LiveData<List<Travel>> loadTravels();

   @Update(onConflict = OnConflictStrategy.REPLACE)
   void update(Travel travel);

   @Delete
   void delete(Travel travel);

   @Query("DELETE FROM travels_table")
   public void nukeTable();

}
