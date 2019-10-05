package com.example.tragether.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.tragether.database.EventDao;
import com.example.tragether.database.SupportDataBase;
import com.example.tragether.database.TravelDao;
import com.example.tragether.database.UserDao;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;


public class Utility {

    Context context;
    FirebaseUtility fbu;
    SupportDataBase sdb;
    UserDao uDao;
    TravelDao tDao;
    EventDao eDao;
    static Date tempCloud;
    static Date tempLocal;
    public static ArrayList<Event> suggestedEv;
    public static ArrayList<Event> userEvents;
    public static ArrayList<Travel> userTravels;
    public static ArrayList<Chat> userChats;
    public static boolean isTravel;
    public static boolean isUEvent;
    public static boolean isSEvent;
    public static boolean loadingT = true;
    public static boolean loadingSE = true;
    public static boolean loadingUE = true;



    public Utility(Context context){
        fbu = FirebaseUtility.getInstance();
        sdb = SupportDataBase.getInstance(context);
        uDao = sdb.userDao();
        tDao = sdb.travelDao();
        eDao = sdb.eventDao();
        this.context = context;
    }

    public void userCreation(Context context){
        if(isNetworkAvailable(context)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    buildUser();
                }
            }).start();

        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    User.setUser(uDao.loadUser(User.getInstance().getEmail()));
                }
            }).start();

        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void buildUser(){

        fbu.getTimestamp();
        tempLocal = uDao.getTimestamp(User.getInstance().getEmail());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("timestampBuildUser", "cloud: " + tempCloud);
                Log.d("timestampBuildUser", "local: " + tempLocal);

                if(tempCloud != null && tempLocal != null){
                    if(tempCloud.compareTo(tempLocal) >= 0){
                        fbu.getUser();
                    }else{
                        User.setUser(uDao.loadUser(User.getInstance().getEmail()));
                        fbu.saveUser(User.getInstance());
                    }

                }else if(tempLocal == null && tempCloud != null){
                    fbu.getUser();
                }
            }
        }).start();
    }

    public void buildUserTravels(){

        final Context ctx = this.context;
        new Thread(new Runnable() {

            ArrayList<Travel> temp = new ArrayList<Travel>();
            @Override
            public void run() {
                temp = (ArrayList<Travel>) tDao.loadTravels();

                if(isNetworkAvailable(ctx)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadingT = true;
                            fbu.getTravels();
                            try {
                                sleep(1500);
                            } catch (InterruptedException e) {

                            }
                            tDao.nukeTable();
                            if(userTravels != null) {
                                for (Travel t : userTravels) {
                                    tDao.insert(t);

                                }
                            }
                            if( userTravels != null && userTravels.size() != 0){
                                isTravel = true;
                            }else{
                                isTravel = false;
                            }
                            loadingT = false;
                        }
                    }).start();

                }else if(temp.size()!= 0){
                    loadingT = true;
                    userTravels = temp;
                    isTravel = true;
                    loadingT = false;

                }else{
                    loadingT = true;
                    isTravel = false;
                    loadingT = false;
                }


                    }
            }).start();


    }

    public void buildUserEvents(){
        final Context ctx = this.context;
        new Thread(new Runnable() {
            ArrayList<Event> temp = new ArrayList<Event>();
            @Override
            public void run() {
                temp = (ArrayList<Event>) eDao.loadMyEvents(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                loadingUE = true;

                if(isNetworkAvailable(ctx)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fbu.getUserEvents();
                            try {
                                sleep(1500);
                            } catch (InterruptedException e) {

                            }
                            tDao.nukeTable();
                            if (userEvents != null) {
                                for (Event e : userEvents) {
                                    eDao.insert(e);

                                }
                            }
                            if(userEvents != null && userEvents.size() != 0){
                                isUEvent = true;
                            }else{
                                isUEvent = false;
                            }
                        }
                    }).start();


                }else if(temp.size()!= 0){

                    userEvents = temp;
                    isUEvent = true;

                }else{
                    isUEvent = false;
                }
                loadingUE = false;
            }
        }).start();
    }

    public void buildSuggEvents(){
        final Context ctx = this.context;
        new Thread(new Runnable() {
            ArrayList<Event> temp = new ArrayList<Event>();
            @Override
            public void run() {
                temp = (ArrayList<Event>) eDao.loadEvents(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                loadingSE = true;

                if(isNetworkAvailable(ctx)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadingSE = true;
                            fbu.getSuggEvents();
                            try {
                                sleep(1500);
                            } catch (InterruptedException e) {

                            }
                            tDao.nukeTable();
                            if(suggestedEv != null) {
                                for (Event e : suggestedEv) {
                                    eDao.insert(e);

                                }
                            }
                            if(suggestedEv!= null && suggestedEv.size() != 0){
                                isSEvent = true;
                            }else{
                                isSEvent = false;

                            }
                            loadingSE = false;
                        }
                    }).start();


                }else if(temp.size()!= 0){
                    loadingSE = true;
                    suggestedEv = temp;
                    isSEvent = true;
                    loadingSE = false;

                }else{
                    loadingSE = true;
                    isSEvent = false;
                    loadingSE = false;
                }
            }

        }).start();
    }

    public String oneToOneChatID(String u1, String u2){
        String id;

        if(u1.compareTo(u2) <0 ){
            id = u1 + u2;
            return id;

        }else{
            id = u2 + u1;
            return id;
        }
    }

    public String getEventOrg(int pos){
        String org;

        org = suggestedEv.get(pos).getOrganizer();

        return org;
    }

    public void getChat(int pos){

        String org, id;

        org = getEventOrg(pos);
        id = oneToOneChatID(FirebaseAuth.getInstance().getCurrentUser().getEmail(), org);
        Chat c = new Chat();
        c.setUsername(org);
        if(userChats.contains(c.getUsername())){

        }
        //fbu.getThread(id, org);

    }
}
