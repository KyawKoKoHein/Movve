package com.kkkh.movve.model;

public class ShowTime {

    private String time;
    private boolean selected;
    private boolean enabled = true;

    public ShowTime(String time, boolean selected) {
        this.time = time;
        this.selected = selected;
    }

    public String getTime() {
        return time;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}