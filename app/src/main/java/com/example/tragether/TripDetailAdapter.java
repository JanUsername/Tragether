package com.example.tragether;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tragether.model.Event;
import com.example.tragether.model.Travel;


import java.text.SimpleDateFormat;
import java.util.List;

public class TripDetailAdapter extends RecyclerView.Adapter<TripDetailAdapter.TripCustomViewHolder> {
    private List<Travel> tripDetails;



    public class TripCustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCountry, tvCity, tvStartDate, tvEndDate;

        public TripCustomViewHolder(View view) {
            super(view);
            tvCountry = view.findViewById(R.id.tvTripCountry);
            tvCity = view.findViewById(R.id.tvTripTown);
            tvStartDate = view.findViewById(R.id.tvTripDateStart);
            tvEndDate = view.findViewById(R.id.tvTripDateEnd);

        }

    }

    public TripDetailAdapter(List<Travel> tripDetails) {
        this.tripDetails = tripDetails;
    }

    @Override
    public TripCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list, parent, false);


        return new TripCustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripCustomViewHolder holder, int position) {
        SimpleDateFormat dateFormatD = new SimpleDateFormat("dd/MM/yyyy");
        // SimpleDateFormat dateFormatT = new SimpleDateFormat("h:mm a");
        Travel tripDetail = tripDetails.get(position);
        holder.tvCountry.setText(tripDetail.getCountry());
        holder.tvCity.setText(tripDetail.getTown());
        holder.tvStartDate.setText(String.valueOf(dateFormatD.format(tripDetail.getStart())));
        holder.tvEndDate.setText(String.valueOf(dateFormatD.format(tripDetail.getEnd())));
    }

    @Override
    public int getItemCount() {
        return tripDetails.size();
    }}
