package com.example.tragether.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

//import com.example.tragether.database.SupportDataBase;
import com.example.tragether.database.UserDao;

public class UserRepository {

    private FirebaseUtility fbu;
    private UserDao userDao;
    private LiveData<User> user;

    public UserRepository(Application app){
        fbu = FirebaseUtility.getInstance();
        user = fbu.getUser();
        //SupportDataBase dataBase = SupportDataBase.getInstance(app);
        //userDao = dataBase.userDao();
    }

    public LiveData<User> getUser(){

        return User.getInstance();
    }

}
