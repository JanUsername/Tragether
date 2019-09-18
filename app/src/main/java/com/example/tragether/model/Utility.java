package com.example.tragether.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.tragether.database.SupportDataBase;
import com.example.tragether.database.UserDao;

import java.util.Date;


public class Utility {

    FirebaseUtility fbu;
    SupportDataBase sdb;
    UserDao dao;
    static Date tempCloud;
    static Date tempLocal;

    public Utility(Context context){
        fbu = FirebaseUtility.getInstance();
        sdb = SupportDataBase.getInstance(context);
        dao = sdb.userDao();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void buildUser(){

        fbu.getTimestamp();
        tempLocal = dao.getTimestamp(User.getInstance().getEmail());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("timestampBuildUser", "cloud: " + tempCloud);
                Log.d("timestampBuildUser", "local: " + tempLocal);

                if(tempCloud != null && tempLocal != null){
                    if(tempCloud.compareTo(tempLocal) >= 0){
                        fbu.getUser();
                    }else{
                        User.setUser(dao.loadUser(User.getInstance().getEmail()));
                        fbu.saveUser(User.getInstance());
                    }

                }else if(tempLocal == null && tempCloud != null){
                    fbu.getUser();
                }
            }
        }).start();




    }

}
