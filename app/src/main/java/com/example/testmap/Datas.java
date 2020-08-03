package com.example.testmap;

public class Datas {
    String quality,quantity,cost,bunkName;
    public  Datas(){

    }
    public Datas(String bunkName,String quality,String quantity,String cost){
        this.bunkName=bunkName;
        this.quality=quality;
        this.quantity=quantity;
        this.cost=cost;
    }

    public String getBunkName() {
        return bunkName;
    }

    public void setBunkName(String bunkName) {
        this.bunkName = bunkName;
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
