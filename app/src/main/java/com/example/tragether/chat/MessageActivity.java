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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.R;
import com.example.tragether.model.Message;
import com.example.tragether.model.User;

import java.util.ArrayList;


public class MessageActivity extends AppCompatActivity {

    Bundle extras;
    Toolbar tb;
    ImageButton send;
    EditText sendTxt;
    MessageDetailAdapter mAdapter;
    ArrayList<Message> thread;
    RecyclerView recyclerView;


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

        recyclerView = findViewById(R.id.recycler_view_msg);
        thread = new ArrayList<>();
        mAdapter = new MessageDetailAdapter(thread);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        fillThread();

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

    private void fillThread(){
        thread.add(new Message(User.getInstance().getEmail(), "user1", "ciao"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "ciao"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "come va? Tutto bene spero"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "Potresti fare tu la spesa?"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "farò il possibile"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "grazie! Ciao"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "ciao"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "ciao"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "come va? Tutto bene spero"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "Potresti fare tu la spesa?"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "farò il possibile"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "grazie! Ciao"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "ciao"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "ciao"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "come va? Tutto bene spero"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "Potresti fare tu la spesa?"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "farò il possibile"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "grazie! Ciao"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "ciao"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "ciao"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "come va? Tutto bene spero"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "Potresti fare tu la spesa?"));
        thread.add(new Message("user1", User.getInstance().getEmail(), "farò il possibile"));
        thread.add(new Message(User.getInstance().getEmail(), "user1", "grazie! Ciao"));
    }
}
