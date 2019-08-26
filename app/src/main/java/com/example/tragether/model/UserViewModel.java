package com.example.tragether.model;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tragether.UserRepository;

import java.util.ArrayList;
import java.util.Date;

public class UserViewModel extends ViewModel {

    private UserRepository repository;
    private MutableLiveData<User> userLiveData;

    public void init(){

            repository = UserRepository.getInstance();
            userLiveData = repository.getUser();

    }

    public LiveData<User> getUser(){
        return userLiveData;
    }

    public void updateUsername(String username){
        User temp = User.getInstance();
        temp.setUsername(username);
        userLiveData.postValue(temp);
    }

    public void updateBirthday(Date bDay){
        User temp = User.getInstance();
        temp.setBirthday(bDay);
        userLiveData.postValue(temp);
    }

    public void updateCountry(String country){
        User temp = User.getInstance();
        temp.setCountry(country);
        userLiveData.postValue(temp);
    }

    public void updateInterests(ArrayList interests){
        User temp = User.getInstance();
        temp.setInterests(interests);
        userLiveData.postValue(temp);
    }

    public void updateDescription(String description){
        User temp = User.getInstance();
        temp.setDescription(description);
        userLiveData.postValue(temp);
    }




}
