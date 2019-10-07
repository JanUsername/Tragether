package com.example.tragether.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tragether.model.Travel;

import java.util.List;

public class TravelViewModel extends AndroidViewModel {
    private TravelRepository travelRepository;
    private List<Travel> allTravels;

    public TravelViewModel(@NonNull Application application) {
        super(application);
        travelRepository = new TravelRepository(application);
        allTravels = travelRepository.getAllTravels();
    }

    public void insert(Travel travel) {
        travelRepository.insertTravel(travel);
    }

    public void update(Travel travel) {
        travelRepository.updateTravel(travel);
    }

    public void delete(Travel travel) {
        travelRepository.deleteTravel(travel);
    }

    public List<Travel> getAllTravels() {
        return allTravels;
    }
}
