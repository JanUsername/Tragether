package com.example.tragether;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.model.Chat;

import java.util.ArrayList;

public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailAdapter.CustomViewHolder> {

    private ArrayList<Chat> chats;

    public ChatDetailAdapter (ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameChat;

        public CustomViewHolder(View view) {
            super(view);
            usernameChat = view.findViewById(R.id.usernameChat);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list, parent, false);

        return new ChatDetailAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Chat c = chats.get(position);
        holder.usernameChat.setText(c.getUsername());

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

}
