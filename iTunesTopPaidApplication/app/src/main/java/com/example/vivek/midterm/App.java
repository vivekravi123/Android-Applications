package com.example.vivek.midterm;


public class App {
    String name,cost,image;
    int Id;

    public App(String name, String cost, String image) {
        this.name = name;
        this.cost = cost;
        this.image = image;
    }
    public App() {

    }
    public String getName() {
        return name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
