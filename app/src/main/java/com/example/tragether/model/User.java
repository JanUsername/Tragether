package com.example.tragether.model;

import android.media.Image;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;


@Entity(tableName = "user_table")
public class User {

    public static User userInstance = null;

    @PrimaryKey
    private @NonNull String email;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "birthday")
    private Date birthday;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "interests")
    private ArrayList<String> interests;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "timestamp")
    private Date timestamp;
/*
    @Ignore
    private Image profilePic;
    @Ignore
    private ArrayList<Travel> travels;
    @Ignore
    private ArrayList<User> friends;
    @Ignore
    private ArrayList<Integer> eventID

*/

    public static User getInstance(){

        if(userInstance == null){
            userInstance = new User();
        }

        return userInstance;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
/*
    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }

    public ArrayList<Travel> getTravels() {
        return travels;
    }

    public void setTravels(ArrayList<Travel> travels) {
        this.travels = travels;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }*/

    public void resetUser(){
        username = "";
        birthday = null;
        country = "";
        interests = null;
        description = "";
       // profilePic = null;
       // travels = null;
       // friends = null;

    }

    public static void setUser(User user){
        userInstance = user;
    }

}
