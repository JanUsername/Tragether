package com.example.tragether;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.tragether.R.id.textView;

public class logged_activity extends AppCompatActivity {


    TextView txt_logged_email;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_activity);
        txt_logged_email = (TextView)findViewById(textView);
        intent = this.getIntent();
        Log.d("check", getIntent().toString());

        txt_logged_email.setText("Hello " + getIntent().getStringExtra("email"));

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
