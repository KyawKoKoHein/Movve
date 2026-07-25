package com.kkkh.movve.model;

public class DateItem {

    private String date;
    private String day;
    private boolean selected;

    public DateItem(String date, String day, boolean selected) {
        this.date = date;
        this.day = day;
        this.selected = selected;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}