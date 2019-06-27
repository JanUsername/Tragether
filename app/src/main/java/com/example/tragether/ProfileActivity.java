package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity  extends  MenuHandler {

    ImageView imageView;
    TextView greetingsProfile;
    Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.imageView1);
        int imageRes = getResources().getIdentifier("@drawable/cat", null, this.getPackageName());
        imageView.setImageResource(imageRes);

        greetingsProfile = (TextView)findViewById(R.id.greetingsProfile);
        greetingsProfile.setText("Hello " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        editProfile = (Button) findViewById(R.id.btnGoToProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ProfileActivity.this, logged_activity.class));
    }

}
