package com.example.androidproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * This class's purpose is just to provide the structure of the cities in the realtime database
 * json tree and make the initial changes in the db. Usually these classes are used for dynamically
 * adding data (user input), but in this case we'll just use it to populate the database.
 * i.e. No user will add a city to the db.
 */
public class City implements Parcelable {

    private String iataCode;
    private String name;
    // [nature, partying, culture, relaxation]
    private String country;
    private ArrayList<String> tags;
    private String description;
    // [cloudy, rainy, sunny, snowy]
    public String weather;
    // We might want to make this an int array later, to be able to display multiple
    // images of the same city
    private String image;

    public City() {

    }

    protected City(Parcel in) {
        iataCode = in.readString();
        name = in.readString();
        country = in.readString();
        tags = in.createStringArrayList();
        description = in.readString();
        weather = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iataCode);
        dest.writeString(name);
        dest.writeString(country);
        dest.writeStringList(tags);
        dest.writeString(description);
        dest.writeString(weather);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public void setIataCode(String iataCode) {this.iataCode = iataCode;}
    public void setName(String name){
        this.name = name;
    }
    public void setCountry(String country){this.country = country;}
    public void setTags(ArrayList<String> tags){
        this.tags = tags;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setWeather(String weather){
        this.weather = weather;
    }
    public void setImage(String image){
        this.image = image;
    }

    public String getIataCode(){return this.iataCode;}
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
    public String getImage(){
        return this.image;
    }


}
