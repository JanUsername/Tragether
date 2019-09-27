package com.example.tragether.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.R;
import com.example.tragether.model.Message;

import java.util.ArrayList;


public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.MsgViewHolder>  {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private ArrayList<Message> msgs;

    public MessageDetailAdapter(ArrayList<Message> msgs) {
        this.msgs = msgs;
    }


    public class MsgViewHolder extends RecyclerView.ViewHolder {
        public TextView show_msg;

        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);
            show_msg = itemView.findViewById(R.id.show_msg);
        }
    }

    @NonNull
    @Override
    public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list, parent, false);

        return new MessageDetailAdapter.MsgViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }
}
