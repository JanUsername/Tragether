package com.example.tragether;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tragether.model.Event;


import java.text.SimpleDateFormat;
import java.util.List;

public class EventDetailAdapter extends RecyclerView.Adapter<EventDetailAdapter.CustomViewHolder> {
    private List<Event> eventDetails;



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView eventNameView, dateView, locationView, timeView;

        public CustomViewHolder(View view) {
            super(view);
            eventNameView = view.findViewById(R.id.eventName);
            locationView = view.findViewById(R.id.location);
            dateView = view.findViewById(R.id.date);
            timeView = view.findViewById(R.id.time);

        }

    }

    public EventDetailAdapter(List<Event> eventDetails) {
        this.eventDetails = eventDetails;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list, parent, false);


        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        SimpleDateFormat dateFormatD = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatT = new SimpleDateFormat("h:mm a");
        Event eventDetail = eventDetails.get(position);
        holder.eventNameView.setText(eventDetail.getTitle());
        holder.locationView.setText(eventDetail.getCountry() + ", " + eventDetail.getTown());
        holder.dateView.setText(String.valueOf(dateFormatD.format(eventDetail.getStart())));
        holder.timeView.setText(String.valueOf(dateFormatT.format(eventDetail.getStartTime())));
    }

    @Override
    public int getItemCount() {
        return eventDetails.size();
    }}