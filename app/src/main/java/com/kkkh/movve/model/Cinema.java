package com.kkkh.movve.model;

public class Cinema {

    private int image;
    private String name;
    private String location;
    private String screen;

    private String selectedTime = "";


    public Cinema(int image,
                  String name,
                  String location,
                  String screen) {

        this.image = image;
        this.name = name;
        this.location = location;
        this.screen = screen;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getScreen() {
        return screen;
    }


    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }
}