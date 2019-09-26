package com.example.tragether;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tragether.model.EventDetail;

import java.util.List;

public class EventDetailAdapter extends RecyclerView.Adapter<EventDetailAdapter.CustomViewHolder> {
    private List<EventDetail> eventDetails;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView eventNameView, dateView, userView, rating;

        public CustomViewHolder(View view) {
            super(view);
            eventNameView = view.findViewById(R.id.eventName);
            userView = view.findViewById(R.id.user);
            dateView = view.findViewById(R.id.date);
            rating = view.findViewById(R.id.rating);
        }
    }

    public EventDetailAdapter(List<EventDetail> eventDetails) {
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
        EventDetail eventDetail = eventDetails.get(position);
        holder.eventNameView.setText(eventDetail.getEventName());
        holder.userView.setText(eventDetail.getUser());
        holder.dateView.setText(String.valueOf(eventDetail.getDate()));
        holder.rating.setText(String.valueOf(eventDetail.getLocation()));
    }

    @Override
    public int getItemCount() {
        return eventDetails.size();
    }
}
