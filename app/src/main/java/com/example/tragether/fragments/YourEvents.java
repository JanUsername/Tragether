package com.example.tragether.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.EventDetailAdapter;
import com.example.tragether.R;
import com.example.tragether.RecyclerItemListener;
import com.example.tragether.ViewModel.EventViewModel;
import com.example.tragether.ViewModel.TravelViewModel;
import com.example.tragether.model.Event;
import com.example.tragether.model.FirebaseUtility;
import com.example.tragether.model.Utility;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class YourEvents extends Fragment {
    View view;
    private int eventNum;


    List<Event> eventDetails = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventDetailAdapter mAdapter;
    private EventViewModel eventViewModel;
    private FirebaseUtility fbu;


    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.profile_fragment_rec_main, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_profile);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);

        fbu = FirebaseUtility.getInstance();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        if(Utility.isUEvent) {
            mAdapter = new EventDetailAdapter(Utility.userEvents);
            recyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
        }

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
    public void onResume() {
        super.onResume();
        mAdapter = new EventDetailAdapter(Utility.userEvents);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.delete_edit_menu_event, contextMenu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Event selected = Utility.userEvents.get(eventNum);
        switch (item.getItemId()){
            case R.id.delete_event:

                //eventViewModel.delete(selected);
                fbu.deleteEvent(selected);
                Utility.userEvents.remove(eventNum);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);

                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }
}