package com.example.tragether.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.example.tragether.model.Event;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository eventRepository;
    private List<Event> allEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allEvents = eventRepository.getAllEvents();
    }

    public void insert(Event event) {
        eventRepository.insertEvent(event);
    }

    public void update(Event event) {
        eventRepository.updateEvent(event);
    }

    public void delete(Event event) {
        eventRepository.deleteEvent(event);
    }

    public List<Event> getAllEvents() {
        return allEvents;
    }
}

