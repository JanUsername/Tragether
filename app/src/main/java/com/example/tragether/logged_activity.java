package com.example.tragether;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class logged_activity extends AppCompatActivity {


    TextView txt_logged_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_activity);

        txt_logged_email = (TextView)findViewById(R.id.textView);
    }
}
