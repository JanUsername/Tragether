package com.example.tragether.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private static User userInstance = null;

    private String username;
    private String email;
    private Date birthday;
    private String country;
    private ArrayList<Interest> interests;
    private String description;
    private Image profilePic;
    private ArrayList<Travel> travels;
    private ArrayList<User> friends;

    public static User getUserInstance() {
        return userInstance;
    }

    public static void setUserInstance(User userInstance) {
        User.userInstance = userInstance;
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

    public ArrayList<Interest> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Interest> interests) {
        this.interests = interests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
    }


    private User(){


    }

    public void resetUser(){
        username = null;
        email = null;
        birthday = null;
        country = null;
        interests = null;
        description = null;
        profilePic = null;
        travels = null;
        friends = null;

    }

    public static User getInstance(){

        if(userInstance == null){
            userInstance = new User();
        }

        return userInstance;

    }

}
