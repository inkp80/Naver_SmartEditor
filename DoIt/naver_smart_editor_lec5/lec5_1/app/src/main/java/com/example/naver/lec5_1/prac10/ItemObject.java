package com.example.naver.lec5_1.prac10;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class ItemObject {


    int resId;
    String name;
    String price;
    String remarks;

    public ItemObject(int resId, String name, String price, String remarks){
        this.resId = resId;
        this.name = name;
        this.price = price;
        this.remarks = remarks;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
