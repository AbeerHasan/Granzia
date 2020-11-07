package com.example.granziaegypt.Models;

import java.util.ArrayList;

public class Product {
    private String id;
    private ArrayList<String> Description = new ArrayList<String>();
    private String Name;
    private String Picture;
    private String Price;




    public Product() {
    }

    public Product(String id, ArrayList<String> description, String name, String picture, String price) {
        this.id = id;
        Description = description;
        Name = name;
        Picture = picture;
        Price = price;
    }

    public ArrayList<String> getDescription() {
        return Description;
    }

    public void setDescription(ArrayList<String> description) {
        Description = description;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}
