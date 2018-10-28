package com.example.initialproject;

public class Info {
    private String name;
    private int imageName;

    public Info(String name, int imageName) {
        this.imageName=imageName;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getImageName() {
        return imageName;
    }
}
