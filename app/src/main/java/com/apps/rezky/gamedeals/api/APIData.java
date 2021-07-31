package com.apps.rezky.gamedeals.api;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    31 juli 2021
*/

public class APIData {
    private String title;
    private String dealprice;
    private String normalprice;
    private String coverImage;
    private String discount;
    private String metacritic;
    private String steamreview;
    private String onsale;

    //constructor
    public APIData(){}
    public APIData(String title, String dealprice, String normalprice, String coverImage, String discount, String metacritic, String steamreview, String onsale){
        this.title = title;
        this.dealprice = dealprice;
        this.normalprice = normalprice;
        this.coverImage = coverImage;
        this.discount = discount;
        this.metacritic = metacritic;
        this.steamreview = steamreview;
        this.onsale = onsale;
    }

    //fungsi setter dan getter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDealPrice() {
        return dealprice;
    }

    public void setDealPrice(String dealprice) {
        this.dealprice = dealprice;
    }

    public String getNormalPrice() {
        return normalprice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalprice = normalPrice;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(String metacritic){
        this.metacritic = metacritic;
    }

    public String getSteamreview() {
        return steamreview;
    }

    public void setSteamreview(String steamreview){
        this.steamreview = steamreview;
    }

    public String getOnSale() {
        return onsale;
    }

    public void setOnSale(String onsale){
        this.onsale = onsale;
    }
}