package com.example.tragether;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.tragether.model.User;


public class UserRepository {

    MutableLiveData<User> user = new MutableLiveData<>();

    private static UserRepository instance = null;

    public static UserRepository getInstance(){

        if(instance == null){

            instance = new UserRepository();
        }
        return instance;
    }


    public MutableLiveData<User> getUser(){

        Log.d("userLog", "" + User.getInstance().toString());

        if(user == null){

            user.postValue(User.getInstance());
        }

        return user;

    }





}

