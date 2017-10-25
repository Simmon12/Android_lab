package com.example.lizhicai.shopapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

/**
 * Created by lizhicai on 2017/10/22.
 */

public class shopCarAdapter extends BaseAdapter {
    private Context context;
    private List<Goods>shopCarGood;

    public shopCarAdapter(Context context, List<Goods>shopCarGood) {
        this.context = context;
        this.shopCarGood = shopCarGood;
    }

    @Override
    public int getCount() {
        if(shopCarGood != null) {
            return shopCarGood.size();
        } else {
            return 0;
        }
    }

   @Override
    public Object getItem(int i) {
       if(shopCarGood == null) {
           return null;
       } else {
           return shopCarGood.get(i);
       }
   }

   @Override
    public long getItemId(int i) {
       return i;
   }

   @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
       View convertView;
       ViewHolder holder;
       if(view == null) {
           convertView = LayoutInflater.from(context).inflate(R.layout.shopcar_item,null);
           holder = new ViewHolder();
           holder.f1 = (TextView)convertView.findViewById(R.id.ic);
           holder.tv = (TextView)convertView.findViewById(R.id.goodName);
           holder.tv2 = (TextView)convertView.findViewById(R.id.cost);
           convertView.setTag(holder);
       } else {
           convertView = view;
           holder = (ViewHolder) convertView.getTag();
       }
       holder.f1.setText(shopCarGood.get(position).getName().substring(0,1).toUpperCase());
       holder.tv.setText(shopCarGood.get(position).getName());
       holder.tv2.setText(shopCarGood.get(position).getPrice());
       return convertView;
   }

   private class ViewHolder {
       public TextView f1;
       public TextView tv;
       public TextView tv2;
   }

}
