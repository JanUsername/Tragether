package com.example.tragether.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tragether.R;
import com.example.tragether.model.User;


public class MessageActivity extends AppCompatActivity {

    Bundle extras;
    Toolbar tb;
    ImageButton send;
    EditText sendTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);

        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        Intent intent = getIntent();
        Log.d("intent", ""+ intent.getStringExtra("user"));
        extras = intent.getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(extras.getString("user"));
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendTxt = findViewById(R.id.text_send);
        send = findViewById(R.id.sendBtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String toSend = sendTxt.getText().toString();
                if(!toSend.equals("")){
                    sendMsg(User.getInstance().getEmail(), extras.getString("user"), toSend);
                }else{
                    Toast.makeText(MessageActivity.this, "you cannot send an empty msg", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void sendMsg(String sender, String receiver, String msg){

        //we need to use the email, not the username!




    }
}
