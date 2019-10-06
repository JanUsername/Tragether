package com.example.tragether.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.R;
import com.example.tragether.TripDetailAdapter;
import com.example.tragether.ViewModel.TravelViewModel;


import javax.annotation.Nullable;

public class YourTrips extends Fragment {
    View view;


    private TravelViewModel travelViewModel;
    private RecyclerView recyclerView;
    private TripDetailAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_rec_main, container, false);

        travelViewModel = new ViewModelProvider(getActivity()).get(TravelViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_profile);
        mAdapter = new TripDetailAdapter(travelViewModel.getAllTravels());
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        mAdapter.notifyDataSetChanged();
        return view;
    }

}