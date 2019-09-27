package com.example.tragether.model;

import androidx.room.*;

import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "events_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "start")
    private Date start;
    @ColumnInfo(name = "end")
    private Date end;
    //tags match with interests
    @ColumnInfo(name = "tags")
    private ArrayList<String> tags;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "town")
    private String town;
    @ColumnInfo(name = "organizer")
    private String organizer;


    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getOrganizer() { return organizer; }

    public void setOrganizer(String organizer) { this.organizer = organizer; }







}
