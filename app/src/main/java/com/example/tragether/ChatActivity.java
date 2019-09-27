package com.example.tragether;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.model.Chat;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

    ArrayList<Chat> chatDetails = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView =  findViewById(R.id.recycler_view_chat);

        mAdapter = new ChatDetailAdapter(chatDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    /*    recyclerView.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.item_separator)));*/

        populateChatsDetails();
        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(),
                recyclerView, new RecyclerItemListener.RecyclerTouchListener() {
            @Override
            public void onClickItem(View v, int position) {
                Toast.makeText(getApplicationContext(), "Clicked: " +
                        chatDetails.get(position).getUsername(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickItem(View v, int position) {
                Toast.makeText(getApplicationContext(), "Long Pressed: " +
                        chatDetails.get(position).getUsername(), Toast.LENGTH_SHORT).show();

            }
        }));

    }

    private void populateChatsDetails() {
        for (int i = 0; i < 5; i ++){
            Chat t = new Chat();
            t.setUsername("user" + i);
            chatDetails.add(t);
        }
    }
}
