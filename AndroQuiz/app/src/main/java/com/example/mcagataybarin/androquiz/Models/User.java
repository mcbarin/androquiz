package com.example.mcagataybarin.androquiz.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class User {

    public String username;
    public String name;
    public String surname;
    public String city;
    public String email;
    public ArrayList<String> friends, requests;

    public User() {

    }

    public User(String username, String name,String surname, String city, String email) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.city = city;
    }

    public User(HashMap<String, Object> event_info) {
        this.username = (String) event_info.get("username");
        this.city = (String) event_info.get("city");
        this.name = (String) event_info.get("name");
        this.surname = (String) event_info.get("surname");
        this.requests = (ArrayList<String>) event_info.get("requests");
        this.friends = (ArrayList<String>) event_info.get("friends");
        this.city = (String) event_info.get("city");
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("surname", surname);
        result.put("city", city);
        result.put("name", name);
        this.friends = (ArrayList<String>) result.put("friends", friends);
        this.requests = (ArrayList<String>) result.put("requests", requests);
        result.put("email", email);

        return result;
    }

    public String toString() {
        return this.name + " " + this.email + " " + this.city + " " + this.surname + " " + this.username + " ";
    }

}

