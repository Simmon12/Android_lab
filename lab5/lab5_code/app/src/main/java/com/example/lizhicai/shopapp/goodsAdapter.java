package com.example.lizhicai.shopapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lizhicai on 2017/10/22.
 */

public class goodsAdapter extends RecyclerView.Adapter<goodsAdapter.MyViewHolder> {
    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    private OnItemClickListener mOnItemClickListener = null;
    private List<Goods>good;
    private Context context;
    public goodsAdapter(List<Goods>good, Context context) {
        this.good = good;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gooditem,parent, false);
        MyViewHolder viewholder = new MyViewHolder(view);
        return viewholder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        TextView tv2;
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView)view.findViewById(R.id.circle);
            tv2 = (TextView)view.findViewById(R.id.good_id);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(good.get(position).getName().substring(0,1).toUpperCase());
        holder.tv2.setText(good.get(position).getName());
        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return good.size();
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }



}
