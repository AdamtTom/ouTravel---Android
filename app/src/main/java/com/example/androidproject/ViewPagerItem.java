package com.example.androidproject;

public class ViewPagerItem {
    int image;
    String city, country, description;

    public ViewPagerItem(int image, String city, String country, String description) {
        this.image = image;
        this.city = city;
        this.country = country;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
