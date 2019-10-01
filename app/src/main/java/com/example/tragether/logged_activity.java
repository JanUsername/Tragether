package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.model.*;
import com.example.tragether.database.*;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class logged_activity extends  MenuHandler {

    TextView welcome;
    TextView feedTxt;
    User appUser;
    FirebaseUtility fbu;
    SupportDataBase sdb;
    UserDao dao;
    Utility utility;
    RecyclerView recyclerView;
    EventDetailAdapter mAdapter;
    ArrayList<Event> eventDetails;

    /*
    In this activity we will show a recycle view for the suggested events
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        utility = new Utility(getApplicationContext());
        sdb = SupportDataBase.getInstance(getApplicationContext());
        dao = sdb.userDao();

        fbu.getInterests();

        setContentView(R.layout.activity_logged_activity);


        welcome = findViewById(R.id.welcome);
        feedTxt = findViewById(R.id.feedTxt);

        recyclerView = findViewById(R.id.recycler_view_events);
        eventDetails = new ArrayList<>();

        fillEvents();

        mAdapter = new EventDetailAdapter(eventDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onStart(){
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {

                appUser.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                utility.userCreation(getApplicationContext());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void fillEvents() {

        String org = "simply_la_gre@hotmail.it";
        Event t1 = new Event();
        Event t2 = new Event();
        Event t3 = new Event();
        Event t4 = new Event();
        Event t5 = new Event();
        Event t6 = new Event();
        Event t7 = new Event();

        SimpleDateFormat dateFormatD = new SimpleDateFormat("dd/MM/yyyy");
        try {
            t1.setStart(dateFormatD.parse("02/10/2019"));
            t2.setStart(dateFormatD.parse("05/10/2019"));
            t3.setStart(dateFormatD.parse("08/10/2019"));
            t4.setStart(dateFormatD.parse("12/11/2019"));
            t5.setStart(dateFormatD.parse("13/01/2020"));
            t6.setStart(dateFormatD.parse("16/02/2020"));
            t7.setStart(dateFormatD.parse("28/02/2020"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            t1.setStartTime(dateFormatD.parse("18:30"));
            t2.setStartTime(dateFormatD.parse("10:30"));
            t3.setStartTime(dateFormatD.parse("11:00"));
            t4.setStartTime(dateFormatD.parse("22:30"));
            t5.setStartTime(dateFormatD.parse("20:15"));
            t6.setStartTime(dateFormatD.parse("14:00"));
            t7.setStartTime(dateFormatD.parse("06:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        t1.setTitle("Music night");
        t2.setTitle("Swimming");
        t3.setTitle("Museum");
        t4.setTitle("Party");
        t5.setTitle("Aperitiv");
        t6.setTitle("Beach");
        t7.setTitle("After party");

        t1.setOrganizer(org);
        t2.setOrganizer(org);
        t3.setOrganizer(org);
        t4.setOrganizer(org);
        t5.setOrganizer(org);
        t6.setOrganizer(org);
        t7.setOrganizer(org);

        eventDetails.add(t1);
        eventDetails.add(t2);
        eventDetails.add(t3);
        eventDetails.add(t4);
        eventDetails.add(t5);
        eventDetails.add(t6);
        eventDetails.add(t7);

    }
}
