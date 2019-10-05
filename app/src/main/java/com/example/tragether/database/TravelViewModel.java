package com.example.tragether.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tragether.model.TravelRepository;
import com.example.tragether.model.Travel;

import java.util.List;

public class TravelViewModel extends AndroidViewModel {
    private TravelRepository travelRepository;
    private LiveData<List<Travel>> allTravels;

    public TravelViewModel(@NonNull Application application){
        super(application);
        travelRepository = new TravelRepository(application);
        allTravels = travelRepository.getAllTravels();
    }

    public void insert(Travel travel){
        travelRepository.insertTravel(travel);
    }
    public void update(Travel travel){
        travelRepository.updateTravel(travel);
    }
    public void delete(Travel travel){
        travelRepository.deleteTravel(travel);
    }
    public LiveData<List<Travel>> getAllTravels(){
        return allTravels;
    }
}
