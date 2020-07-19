package com.example.testmap;

public class Datas {
    String quality,quantity,cost;
    public  Datas(){

    }
    public Datas(String quality,String quantity,String cost){
        this.quality=quality;
        this.quantity=quantity;
        this.cost=cost;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


}
