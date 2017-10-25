package com.example.lizhicai.shopapp;

/**
 * Created by lizhicai on 2017/10/22.
 */

public class Goods {
    private String name;
    private String info;
    private  String price;

    public Goods(String name, String info, String price) {
        this.name = name;
        this.info = info;
        this.price = price;
    }

    public String getName() { return name; }
    public String getInfo() { return info;}
    public String getPrice() { return price;}
}
