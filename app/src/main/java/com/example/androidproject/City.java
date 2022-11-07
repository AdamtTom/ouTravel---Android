package com.example.androidproject;

import java.util.ArrayList;

/**
 * This class's purpose is just to provide the structure of the cities in the realtime database
 * json tree and make the initial changes in the db. Usually these classes are used for dynamically
 * adding data (user input), but in this case we'll just use it to populate the database.
 * i.e. No user will add a city to the db.
 */
public class City {

    private String name;
    // [nature, partying, culture, relaxation]
    private String country;
    private ArrayList<String> tags;
    private String description;
    // [cloudy, rainy, sunny, snowy]
    public String weather;
    // We might want to make this an int array later, to be able to display multiple
    // images of the same city
    private int image;

    public void setName(String name){
        this.name = name;
    }
    public void setCountry(String counrty){this.country = counrty;}
    public void setTags(ArrayList<String> tags){
        this.tags = tags;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setWeather(String weather){
        this.weather = weather;
    }
    public void setImage(int image){
        this.image = image;
    }


    public String  getName(){return this.name;}
    public String getCountry(){return this.country;}
    public ArrayList<String> getTags(){
        return this.tags;
    }
    public String getDescription(){
        return this.description;
    }
    public String getWeather(){
        return this.weather;
    }
    public int getImage(){
        return this.image;
    }


}
