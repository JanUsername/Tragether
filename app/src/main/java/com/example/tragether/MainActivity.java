package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tragether.database.SupportDataBase;
import com.example.tragether.database.UserDao;
import com.example.tragether.model.FirebaseUtility;
import com.example.tragether.model.User;
import com.example.tragether.model.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText user;
    EditText password;
    Button signIn;
    FirebaseUtility fbu;
    User appUser;
    SupportDataBase sdb;
    UserDao dao;
    Utility utility;
    FirebaseUser fbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        fbu = FirebaseUtility.getInstance();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        appUser = User.getInstance();
        utility = new Utility(getApplicationContext());
        sdb = SupportDataBase.getInstance(getApplicationContext());
        dao = sdb.userDao();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {

            /*utility.buildUserTravels(getApplicationContext());
            utility.buildUserEvents(getApplicationContext());


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        utility.buildSuggEvents(getApplicationContext());
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(MainActivity.this, logged_activity.class));
                }
            }).start();*/
            startActivity(new Intent(MainActivity.this, LoadingActivity.class));

        }

        setContentView(R.layout.content_main);
        user = findViewById(R.id.eTUsername);
        password = findViewById(R.id.eTPassword);

        signIn = findViewById(R.id.btnSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(user.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                  /*  fbu.getTravels();
                                    fbu.getUserEvents();

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                sleep(3000);
                                                fbu.getSuggEvents();
                                                sleep(3000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            startActivity(new Intent(MainActivity.this, logged_activity.class));
                                        }
                                    }).start();*/
                                    startActivity(new Intent(MainActivity.this, LoadingActivity.class));

                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });



    }



}
