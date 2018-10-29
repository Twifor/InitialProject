package com.example.initialproject;

public class Info {
    private String name;
    private String image;
    private int ID;
    private int type;
    private String date;

    public Info(String name, String imageName, int type, int ID, String date) {
        this.image = imageName;
        this.name = name;
        this.type = type;
        this.ID = ID;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return image;
    }

    public int getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }
}
