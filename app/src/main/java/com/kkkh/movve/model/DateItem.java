package com.kkkh.movve.model;

public class DateItem {

    private String date;
    private String day;
    private boolean selected;
    private String fullDate;

    public DateItem(String date, String day,String fullDate, boolean selected) {
        this.fullDate = fullDate;
        this.date = date;
        this.day = day;
        this.selected = selected;
    }
    public String getFullDate() { return fullDate;  }

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