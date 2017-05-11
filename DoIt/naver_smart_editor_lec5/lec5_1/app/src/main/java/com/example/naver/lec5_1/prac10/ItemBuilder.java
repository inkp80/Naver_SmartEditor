package com.example.naver.lec5_1.prac10;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class ItemBuilder {
    private int resId;
    private String name;
    private String price;
    private String remarks;

    public ItemBuilder setResId(int resId){
        this.resId = resId;
        return this;
    }

    public ItemBuilder setName(String name){
        this.name = name;
        return this;
    }

    public ItemBuilder setPrice(String price){
        this.price = price;
        return this;
    }

    public  ItemBuilder setRemarks(String remarks){
        this.remarks = remarks;
        return this;
    }

    public ItemObject Build(){
        ItemObject item = new ItemObject(resId, name, price, remarks);
        return item;
    }

}
