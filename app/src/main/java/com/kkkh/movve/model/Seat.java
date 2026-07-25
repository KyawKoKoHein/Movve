package com.kkkh.movve.model;

public class Seat {

    private String number;
    private boolean reserved;
    private boolean selected;

    public Seat(String number,
                boolean reserved){

        this.number = number;
        this.reserved = reserved;
        this.selected = false;
    }

    public String getNumber(){
        return number;
    }

    public boolean isReserved(){
        return reserved;
    }

    public void setReserved(boolean reserved){
        this.reserved = reserved;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

}