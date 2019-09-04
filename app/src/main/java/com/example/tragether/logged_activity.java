package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.tragether.model.FirebaseUtility;
import com.example.tragether.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.tragether.R.id.textView;

public class logged_activity extends  MenuHandler {


    TextView txt_logged_email;
    Button logOut;
    Button goToProfile;
    User appUser;
    FirebaseUtility fbu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        setContentView(R.layout.activity_logged_activity);

        txt_logged_email = findViewById(textView);
        logOut = findViewById(R.id.btnLogOut);

        appUser.setEmail(user.getEmail());

        fbu.getUser();

        txt_logged_email = (TextView)findViewById(textView);
        logOut = (Button)findViewById(R.id.btnLogOut);

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

        goToProfile = (Button)findViewById(R.id.btnGoToProfile);

        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(logged_activity.this, ProfileActivity.class));
            }
        });


        //intent = this.getIntent();
        Log.d("check", getIntent().toString());

        txt_logged_email.setText("Hello " + getIntent().getStringExtra("email"));

        txt_logged_email.setText("Hello " + user.getEmail());

        UserRepository.getInstance();


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
    }

}
