package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tragether.model.Utility;

import static java.lang.Thread.sleep;

public class LoadingActivity extends AppCompatActivity {

    TextView msg;
    ProgressBar loading;
    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        msg = findViewById(R.id.loadingMsg);
        loading = findViewById(R.id.progressLoading);
        utility = new Utility(getApplicationContext());

        new Thread(new Runnable() {
            @Override
            public void run() {

                utility.buildUserEvents();
                utility.buildUserTravels();


                while(Utility.loadingT){
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                utility.buildSuggEvents();
                while(Utility.loadingSE){
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                startActivity(new Intent(getApplicationContext(), logged_activity.class));


            }
        }).start();

    }
}
