package com.example.tragether.model;


import java.util.ArrayList;

public class Chat {

    private String user;
    private ArrayList<Message> thread;


    public String getUsername(){ return user; }
    public void setUsername(String user) { this.user = user; }

    public ArrayList<Message> getThread(){ return thread; }
    public void setThread(ArrayList<Message> thread) { this.thread = thread; }

}
