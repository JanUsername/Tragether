package com.example.tragether.model;

import androidx.room.*;
import java.util.Date;

@Entity(tableName = "travels_table")
public class Travel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "town")
    private String town;
    @ColumnInfo(name = "start")
    private Date start;
    @ColumnInfo(name = "end")
    private Date end;


    public int getId(){return id;}

    public void setId(int id) { this.id = id;}

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





}
