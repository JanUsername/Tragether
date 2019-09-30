package com.example.tragether.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.EventDetailAdapter;
import com.example.tragether.R;
import com.example.tragether.model.EventDetail;

import java.util.ArrayList;
import java.util.List;

public class YourEvents extends Fragment {
    View view;


    List<EventDetail> eventDetails = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventDetailAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_your_events, container, false);
        //works till here
        recyclerView =  recyclerView.findViewById(R.id.recycler_view);
        mAdapter = new EventDetailAdapter(eventDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        populateMovieDetails();
        mAdapter.notifyDataSetChanged();
        return view;
    }
    private void populateMovieDetails() {
        eventDetails.add(new EventDetail("new Fragment view", "user 1", "25.12", "Bolzano,Italy"));
        eventDetails.add(new EventDetail("Chilling", "user 2", "25.1", "Bolzano,Italy"));
    }
}
