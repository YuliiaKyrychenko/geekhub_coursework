package com.geekhub.models;

public class Performance {
    private int id;
    private String name;
    private String date;
    private String place;
    private double price;

    public Performance(int id, String name, String date, String place, double price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.place = place;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
