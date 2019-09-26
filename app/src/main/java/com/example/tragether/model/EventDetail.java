package com.example.tragether.model;


public class EventDetail {

    private String eventName;
    private String user;
    private String date;
    private String location;

    public EventDetail(String eventName, String user, String date, String location) {
        this.eventName = eventName;
        this.user = user;
        this.date = date;
        this.location = location;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

}