package com.apps.rezky.gamedeals.view.walkthrough;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    30 juli 2021
*/

public class ScreenItem {

    //inisialisasi variable
    String Title,Desc;
    int Screenimg;

    //constructor
    public ScreenItem(String title, String desc, int screenimg) {
        Title = title;
        Desc = desc;
        Screenimg = screenimg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesc() {
        return Desc;
    }

    public int getScreenimg() {
        return Screenimg;
    }

}
