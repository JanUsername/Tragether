package com.example.tragether.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import com.example.tragether.database.EventDao;
import com.example.tragether.model.Event;
import com.example.tragether.model.Utility;

import java.util.List;

public class EventRepository {
    private EventDao eventDao;
    private List<Event> allEvents;

    public EventRepository(Application application) {


        allEvents = Utility.userEvents;
    }

    public void insertEvent(Event event) {
        new InsertEventAsyncTask(eventDao).execute(event);

    }

    public void updateEvent(Event event) {
        new UpdateEventAsyncTask(eventDao).execute(event);

    }

    public void deleteEvent(Event event) {
        new DeleteEventAsyncTask(eventDao).execute(event);

    }

    public List<Event> getAllEvents() {
        return allEvents;
    }

    //has to be static otherwise memory leaks can happen
    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {
        //call again eventDao for db operations
        private EventDao eventDao;

        private InsertEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            //just need to insert in the one hence the 0(first)
            eventDao.insert(events[0]);
            return null;
        }
    }

    //has to be static otherwise memory leaks can happen
    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {
        //call again eventDao for db operations
        private EventDao eventDao;

        private UpdateEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            //just need to insert in the one hence the 0(first)
            eventDao.update(events[0]);
            return null;
        }
    }

    //has to be static otherwise memory leaks can happen
    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {
        //call again eventDao for db operations
        private EventDao eventDao;

        private DeleteEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            //just need to insert in the one hence the 0(first)
            eventDao.delete(events[0]);
            return null;
        }
    }
}

