package com.example.tragether.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.OnConflictStrategy;
import com.example.tragether.model.User;



@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table WHERE email LIKE :uEmail")
    User loadUser(String uEmail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Delete
    void delete(User user);



}
