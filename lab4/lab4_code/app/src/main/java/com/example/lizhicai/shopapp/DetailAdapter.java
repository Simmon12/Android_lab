package com.example.lizhicai.shopapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhicai on 2017/10/22.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder>{
    private static String DYNAMICACTION = "com.example.lizhicai.MyDynamicFilter";
    Activity act;
    private Context context;
    private String Name;
    private String Price;
    private String Info;
    private int cnt = 0;
    private List<String> string;

    public DetailAdapter(String Name, String Price, String Info, Context context, Activity act) {
        this.Name = Name;
        this.context = context;
        this.Price = Price;
        this.Info = Info;
        this.act = act;
    }
    public DetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail,parent, false);
        DetailAdapter.MyViewHolder holder = new DetailAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public int getItemCount() { return 1;}

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(Name);
        holder.tv2.setText(Price);
        holder.tv3.setText(Info);
        String[] funInfo = new String[]{"一键下单","分享商品","不感兴趣","查看更多促销信息"};
        string = new ArrayList<String>();
        for(int i =0; i < funInfo.length; i++) {
            string.add(funInfo[i]);
        }

        functionAdapter fun = new functionAdapter(context, string);
        holder.glist.setAdapter(fun);

        final Intent intent = act.getIntent();
        act.setResult(1, intent);

        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.finish();
            }
        });

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = holder.star.getTag();
                if(tag == "0") {
                    holder.star.setTag("1");
                    holder.star.setImageResource(R.drawable.full_star);
                } else {
                    holder.star.setTag("0");
                    holder.star.setImageResource(R.drawable.empty_star);
                }
            }
        });

        holder.addgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBroadcast = new Intent();
                intentBroadcast.setAction(DYNAMICACTION);
                intentBroadcast.putExtra("name", Name);
                context.sendBroadcast(intentBroadcast);
                Toast.makeText(context, "商品已添加到购物车", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new eventBus(0, Name, Price, Info));
            }
        });

        switch (Name) {
            case "Enchated Forest":
                holder.pc.setImageResource(R.mipmap.enchatedforest);
                break;
            case "Arla Milk":
                holder.pc.setImageResource(R.mipmap.arla);
                break;
            case "Devondale Milk":
                holder.pc.setImageResource(R.mipmap.devondale);
                break;
            case "Kindle Oasis":
                holder.pc.setImageResource(R.mipmap.kindle);
                break;
            case "waitrose 早餐麦片":
                holder.pc.setImageResource(R.mipmap.waitrose);
                break;
            case "Mcvitie's 饼干":
                holder.pc.setImageResource(R.mipmap.mcvitie);
                break;
            case "Ferrero Rocher":
                holder.pc.setImageResource(R.mipmap.ferrero);
                break;
            case "Maltesers":
                holder.pc.setImageResource(R.mipmap.maltesers);
                break;
            case "Lindt":
                holder.pc.setImageResource(R.drawable.lindt);
                break;
            case "Borggreve":
                holder.pc.setImageResource(R.drawable.borggreve);
                break;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tv2;
        TextView tv3;
        ImageView pc;
        ImageButton back;
        ImageButton star;
        ImageButton addgoods;
        ListView glist;
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView)view.findViewById(R.id.name);
            tv2 = (TextView)view.findViewById(R.id.price);
            tv3 = (TextView)view.findViewById(R.id.weight);
            pc = (ImageView)view.findViewById(R.id.pic);
            back = (ImageButton)view.findViewById(R.id.back);
            star = (ImageButton)view.findViewById(R.id.star);
            addgoods = (ImageButton)view.findViewById(R.id.addShopCar);
            glist = (ListView)view.findViewById(R.id.function);
            star.setTag("0");
        }
    }

}
