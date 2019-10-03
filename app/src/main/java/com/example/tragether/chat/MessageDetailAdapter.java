package com.example.tragether.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.R;
import com.example.tragether.model.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.MsgViewHolder>  {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private ArrayList<Message> msgs = new ArrayList<>();

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
        if(viewType == MSG_TYPE_LEFT){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_left, parent, false);

            return new MessageDetailAdapter.MsgViewHolder(itemView);
        }else{
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_right, parent, false);

            return new MessageDetailAdapter.MsgViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MsgViewHolder holder, int position) {

        Message msg = msgs.get(position);

        holder.show_msg.setText(msg.getMsg());


    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    @Override
    public int getItemViewType(int position) {
       if(msgs.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
           return MSG_TYPE_RIGHT;
       }else{
           return MSG_TYPE_LEFT;
       }
    }
}
