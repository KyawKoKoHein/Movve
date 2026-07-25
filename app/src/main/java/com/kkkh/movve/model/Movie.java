package com.kkkh.movve.model;

public class Movie {

    private int id;
    private int image;
    private String title;

    public Movie(int id,int image,String title){

        this.id=id;
        this.image=image;
        this.title=title;
    }

    public int getId(){
        return id;
    }

    public int getImage(){
        return image;
    }

    public String getTitle(){
        return title;
    }

}
