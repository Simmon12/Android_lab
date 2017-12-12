package com.example.lizhicai.shopapp;

/**
 * Created by lizhicai on 2017/10/30.
 */

public class eventBus {
    private int cnt;
    private String name;
    private String price;
    private String info;

    public eventBus(int cnt, String name, String price, String info) {
        this.cnt = cnt;
        this.name = name;
        this.price = price;
        this.info = info;
    }
    public int getCnt() {
        return cnt;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public String getInfo() {
        return info;
    }
}
