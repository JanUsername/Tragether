package com.example.tragether.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import com.example.tragether.database.TravelDao;
import com.example.tragether.model.Travel;
import com.example.tragether.model.Utility;

import java.util.List;


public class TravelRepository {
    private TravelDao travelDao;

    private List<Travel> allTravels;

    public TravelRepository(Application application) {


        allTravels = Utility.userTravels;
    }

    public void insertTravel(Travel travel) {
        new InsertTravelAsyncTask(travelDao).execute(travel);

    }

    public void updateTravel(Travel travel) {
        new UpdateTravelAsyncTask(travelDao).execute(travel);

    }

    public void deleteTravel(Travel travel) {
        new DeleteTravelAsyncTask(travelDao).execute(travel);

    }

    public List<Travel> getAllTravels() {
        return allTravels;
    }

    //has to be static otherwise memory leaks can happen
    private static class InsertTravelAsyncTask extends AsyncTask<Travel, Void, Void> {
        //call again travelDao for db operations
        private TravelDao travelDao;

        private InsertTravelAsyncTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            //just need to insert in the one hence the 0(first)
            travelDao.insert(travels[0]);
            return null;
        }
    }

    //has to be static otherwise memory leaks can happen
    private static class UpdateTravelAsyncTask extends AsyncTask<Travel, Void, Void> {
        //call again travelDao for db operations
        private TravelDao travelDao;

        private UpdateTravelAsyncTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            //just need to insert in the one hence the 0(first)
            travelDao.update(travels[0]);
            return null;
        }
    }

    //has to be static otherwise memory leaks can happen
    private static class DeleteTravelAsyncTask extends AsyncTask<Travel, Void, Void> {
        //call again travelDao for db operations
        private TravelDao travelDao;

        private DeleteTravelAsyncTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            //just need to insert in the one hence the 0(first)
            travelDao.delete(travels[0]);
            return null;
        }
    }
}
