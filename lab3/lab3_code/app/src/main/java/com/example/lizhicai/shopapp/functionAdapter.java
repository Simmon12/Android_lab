package com.example.lizhicai.shopapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lizhicai on 2017/10/22.
 */

public class functionAdapter extends BaseAdapter{
    private Context context;
    List<String>mFunctions;
    public functionAdapter(Context context, List<String>mFunctions) {
        this.context = context;
        this.mFunctions = mFunctions;
    }
    @Override
    public int getCount() {
        if(mFunctions != null) {
            return mFunctions.size();
        }else {
            return 0;
        }
    }
    @Override
    public Object getItem(int i) {
        if (mFunctions == null) {
            return null;
        }else {
            return mFunctions.get(i);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.function_item,null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.functionItem);
            convertView.setTag(holder);
        }else {
            convertView = view;
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mFunctions.get(position));
        return convertView;

    }

    private class ViewHolder {
        public TextView tv;
    }

}
