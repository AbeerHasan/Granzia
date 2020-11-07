package com.example.granziaegypt.Models;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String id;
    private String Name;
    private String Picture;
    private ArrayList<Product> products = new ArrayList<Product>();


    public Category() {
    }

    public String getId() {
        return id;
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}
