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
        public TextView eventNameView, dateView, userView;

        public CustomViewHolder(View view) {
            super(view);
            eventNameView = view.findViewById(R.id.eventName);
            userView = view.findViewById(R.id.user);
            dateView = view.findViewById(R.id.date);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Event eventDetail = eventDetails.get(position);
        holder.eventNameView.setText(eventDetail.getTitle());
        holder.userView.setText(eventDetail.getOrganizer());
        holder.dateView.setText(String.valueOf(dateFormat.format(eventDetail.getStart())));
    }

    @Override
    public int getItemCount() {
        return eventDetails.size();
    }
}
