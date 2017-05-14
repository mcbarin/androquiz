package com.example.mcagataybarin.androquiz.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class User {

    public String username;
    public String name;
    public String surname;
    public String city;
    public String email;

    public User() {

    }

    public User(String username, String name,String surname, String city, String email) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.city = city;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("surname", surname);
        result.put("city", city);
        result.put("name", name);
        result.put("email", email);

        return result;
    }

    public String toString() {
        return this.name + " " + this.email + " " + this.city + " " + this.surname + " " + this.username + " ";
    }

}

