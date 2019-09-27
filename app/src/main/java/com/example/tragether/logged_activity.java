package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.tragether.model.*;
import com.example.tragether.database.*;
import com.google.firebase.auth.FirebaseAuth;


import static com.example.tragether.R.id.textView;

public class logged_activity extends  MenuHandler {


    TextView txt_logged_email;
    Button logOut;
    Button goToProfile;
    Button addTravel;
    User appUser;
    FirebaseUtility fbu;
    SupportDataBase sdb;
    UserDao dao;
    Utility utility;

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

        txt_logged_email = findViewById(textView);

        txt_logged_email = findViewById(textView);


        addTravel = findViewById(R.id.addTravel);
        addTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(logged_activity.this, TravelActivity.class));

            }
        });


    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
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

                txt_logged_email.setText("Hello " + User.getInstance().getUsername());
            }
        }).start();
    }
}
