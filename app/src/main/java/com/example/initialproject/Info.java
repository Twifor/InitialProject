package com.example.initialproject;

public class Info {
    private String name;
    private int imageName;
    private int ID;
    private int type;
    private String date;

    public Info(String name, int imageName, int type, int ID, String date) {
        this.imageName = imageName;
        this.name = name;
        this.type = type;
        this.ID = ID;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getImageName() {
        return imageName;
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
