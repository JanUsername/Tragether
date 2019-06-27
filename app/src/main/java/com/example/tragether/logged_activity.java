package com.example.tragether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.tragether.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.tragether.R.id.textView;

public class logged_activity extends  MenuHandler {


    TextView txt_logged_email;
    Button logOut;
    Button goToProfile;
    User appUser = User.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        setContentView(R.layout.activity_logged_activity);
<<<<<<< HEAD
        txt_logged_email = findViewById(textView);
        logOut = findViewById(R.id.btnLogOut);
=======

        appUser.setEmail(user.getEmail());
        txt_logged_email = (TextView)findViewById(textView);
        logOut = (Button)findViewById(R.id.btnLogOut);

>>>>>>> profileImplementation
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                appUser.resetUser();

                Intent intent = new Intent(logged_activity.this, MainActivity.class);

                startActivity(intent);
            }
        });
<<<<<<< HEAD
        goToProfile = findViewById(R.id.btnProfile);
=======
        goToProfile = (Button)findViewById(R.id.btnEditProfile);
>>>>>>> profileImplementation
        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(logged_activity.this, ProfileActivity.class));
            }
        });


        //intent = this.getIntent();
        Log.d("check", getIntent().toString());

<<<<<<< HEAD
        //getSupportActionBar().hide();
       // Toolbar toolbar =  findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        txt_logged_email.setText("Hello " + getIntent().getStringExtra("email"));
=======
        txt_logged_email.setText("Hello " + user.getEmail());
>>>>>>> profileImplementation

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

<<<<<<< HEAD
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
 */
=======
    @Override
    public void onStart(){
        super.onStart();

        Log.d("logged", appUser.getEmail());
    }

>>>>>>> profileImplementation
}
