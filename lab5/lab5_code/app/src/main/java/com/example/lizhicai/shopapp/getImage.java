package com.example.lizhicai.shopapp;

/**
 * Created by lizhicai on 2017/10/30.
 */

public class getImage {
    public getImage() {

    }
    static int MapImage(String Name) {
        switch (Name) {
            case "Enchated Forest":
                return R.mipmap.enchatedforest;
            case "Arla Milk":
                return R.mipmap.arla;
            case "Devondale Milk":
                return R.mipmap.devondale;
            case "Kindle Oasis":
                return R.mipmap.kindle;
            case "waitrose 早餐麦片":
                return R.mipmap.waitrose;
            case "Mcvitie's 饼干":
                return R.mipmap.mcvitie;
            case "Ferrero Rocher":
                return R.mipmap.ferrero;
            case "Maltesers":
                return R.mipmap.maltesers;
            case "Lindt":
                return R.mipmap.lindt;
            case "Borggreve":
                return R.mipmap.borggreve;
            default: return R.mipmap.enchatedforest;
        }
    }
}
