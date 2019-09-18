package com.example.tragether;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.tragether.database.SupportDataBase;
import com.example.tragether.database.UserDao;
import com.example.tragether.model.FirebaseUtility;
import com.example.tragether.model.User;
import com.example.tragether.model.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.tragether.R.id.textView;

public class logged_activity extends  MenuHandler {


    TextView txt_logged_email;
    Button logOut;
    Button goToProfile;
    User appUser;
    FirebaseUtility fbu;
    SupportDataBase sdb;
    UserDao dao;
    Utility utility;
    /*
    internet check
    https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        utility = new Utility(getApplicationContext());
        sdb = SupportDataBase.getInstance(getApplicationContext());
        dao = sdb.userDao();


        setContentView(R.layout.activity_logged_activity);

        txt_logged_email = findViewById(textView);
        logOut = findViewById(R.id.btnLogOut);

        txt_logged_email = findViewById(textView);
        logOut = findViewById(R.id.btnLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                appUser.resetUser();

                Intent intent = new Intent(logged_activity.this, MainActivity.class);

                startActivity(intent);
            }
        });

        goToProfile = findViewById(R.id.btnGoToProfile);
        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(logged_activity.this, ProfileActivity.class));
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

        Log.d("logged", appUser.getEmail());
        new Thread(new Runnable() {
            @Override
            public void run() {
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
