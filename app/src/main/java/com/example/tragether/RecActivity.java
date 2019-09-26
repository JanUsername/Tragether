package com.example.tragether;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tragether.model.EventDetail;

import java.util.ArrayList;
import java.util.List;

public class RecActivity extends AppCompatActivity {

    List<EventDetail> eventDetails = new ArrayList<>();
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
                Toast.makeText(getApplicationContext(), "Clicked: " +
                        eventDetails.get(position).getEventName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickItem(View v, int position) {
                Toast.makeText(getApplicationContext(), "Long Pressed: " +
                        eventDetails.get(position).getEventName(), Toast.LENGTH_SHORT).show();

            }
        }));

    }

    private void populateMovieDetails() {
        eventDetails.add(new EventDetail("Sightseeing", "user 1", "25.12", "Bolzano,Italy"));
        eventDetails.add(new EventDetail("Chilling", "user 2", "25.1", "Bolzano,Italy"));
        eventDetails.add(new EventDetail("Party", "user 3", "23.10", "Bolzano,Italy"));
        eventDetails.add(new EventDetail("Good time", "user 4", "11.10", "Bolzano, Italy"));
        eventDetails.add(new EventDetail("etc", "user 1", "25.10", "Bolzano, Italy"));
        eventDetails.add(new EventDetail("Oetzi", "user 1", "25.10", "Bolzano, Italy"));
        eventDetails.add(new EventDetail("Oetzi party", "Jan", "25.10", "Bolzano, Italy"));
        eventDetails.add(new EventDetail("Sightseeing", "user 1", "25.10", "Bolzano, Italy"));
        eventDetails.add(new EventDetail("Sightseeing", "user 1", "25.10", "Bolzano, Italy"));
        eventDetails.add(new EventDetail("Sightseeing", "user 1", "25.10", "here, Italy"));
    }
}
