package com.example.tragether;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static com.example.tragether.R.id.textView;

public class logged_activity extends AppCompatActivity {


    TextView txt_logged_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_activity);
        txt_logged_email = (TextView)findViewById(textView);
        Intent intent = this.getIntent();
        Log.d("check", getIntent().toString());

        txt_logged_email.setText("Hello " + getIntent().getStringExtra("username"));




    }
}
