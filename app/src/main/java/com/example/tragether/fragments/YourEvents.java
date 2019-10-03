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
import com.example.tragether.model.Event;
import com.example.tragether.model.Utility;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class YourEvents extends Fragment {
    View view;


    List<Event> eventDetails = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventDetailAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.profile_fragment_rec_main, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_profile);
        mAdapter = new EventDetailAdapter(Utility.userEvents);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.notifyDataSetChanged();
        return view;
    }

}