package com.example.tragether.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    LiveData<User> userLiveData;

    public UserViewModel(@NonNull Application application) {

        super(application);
        repository = new UserRepository(application);
        userLiveData = repository.getUser();
    }

    public LiveData<User> getUserLiveData(){
        return userLiveData;
    }
}
