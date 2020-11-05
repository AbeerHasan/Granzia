package com.example.granzia.Models;

public class Product {
    private String Description;
    private String Name;
    private String Picture;

    public Product() {
    }

    public Product(String description, String name, String picture) {
        Description = description;
        Name = name;
        Picture = picture;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
