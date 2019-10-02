package com.example.tragether;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tragether.model.Event;

import java.util.ArrayList;
import java.util.List;

public class RecActivity extends AppCompatActivity {

    List<Event> eventDetails = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_main);
        recyclerView =  findViewById(R.id.recycler_view);
        mAdapter = new EventDetailAdapter(eventDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    /*    recyclerView.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.item_separator)));*/

        populateMovieDetails();
        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(),
                recyclerView, new RecyclerItemListener.RecyclerTouchListener() {
            @Override
            public void onClickItem(View v, int position) {

            }

            @Override
            public void onLongClickItem(View v, int position) {
                Toast.makeText(getApplicationContext(), "Long Pressed: " +
                        eventDetails.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }
        }));

    }

    private void populateMovieDetails() {

    }
}
