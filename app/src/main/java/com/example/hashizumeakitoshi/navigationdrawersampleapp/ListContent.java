package com.example.hashizumeakitoshi.navigationdrawersampleapp;


public class ListContent {

    private String text;
    private int resId;


    public ListContent(String text, int resId){
        this.text = text;
        this.resId = resId;
    }

    public String getText(){
        return  this.text;
    }

    public int getResId(){
        return this.resId;
    }


}
