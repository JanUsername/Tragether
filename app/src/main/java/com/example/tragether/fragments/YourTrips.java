package com.example.tragether.fragments;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.GlobalApplication;
import com.example.tragether.R;
import com.example.tragether.RecyclerItemListener;
import com.example.tragether.TripDetailAdapter;
import com.example.tragether.ViewModel.TravelViewModel;
import com.example.tragether.logged_activity;
import com.example.tragether.model.FirebaseUtility;
import com.example.tragether.model.Travel;
import com.example.tragether.model.Utility;


import java.util.ArrayList;

import javax.annotation.Nullable;

public class YourTrips extends Fragment {
    View view;


    private TravelViewModel travelViewModel;
    private RecyclerView recyclerView;
    private TripDetailAdapter mAdapter;
    private FirebaseUtility fbu;
    private int eventNum;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.profile_fragment_rec_main, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fbu = FirebaseUtility.getInstance();

        travelViewModel = new ViewModelProvider(getActivity()).get(TravelViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_profile);
        mAdapter = new TripDetailAdapter(travelViewModel.getAllTravels());
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerItemListener(view.getContext(),
                recyclerView, new RecyclerItemListener.RecyclerTouchListener() {
            @Override
            public void onClickItem(View v, int position) {
                eventNum = position;
                registerForContextMenu(v);
            }

            @Override
            public void onLongClickItem(View v, int position) {
                eventNum = position;
                registerForContextMenu(v);
            }
        }));

    }



    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.delete_edit_menu, contextMenu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Travel t = Utility.userTravels.get(eventNum);
        switch (item.getItemId()){
            case R.id.delete:
                //travelViewModel.delete(t);
                fbu.deleteTravel(t);
                Utility.userTravels.remove(eventNum);
                return true;
            case R.id.edit:

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(mAdapter);
    }
}