package com.example.tragether.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private String username;
    private String email;
    private Date birthday;
    private String country;
    private ArrayList<Interest> interests;
    private String description;
    private Image profilePic;
    private ArrayList<Travel> travels;
    private ArrayList<User> friends;

}
