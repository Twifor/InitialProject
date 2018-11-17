package com.example.initialproject;

public class Info {
    private String name;
    private String image;
    private int ID;
    private String date;
    private ItemType itemType;

    Info(String name, String imageName, int ID, String date, ItemType itemType) {
        this.image = imageName;
        this.name = name;
        this.ID = ID;
        this.itemType = itemType;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    String getImageName() {
        return image;
    }

    public int getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }

    public ItemType getType() {
        return itemType;
    }
}
