package com.example.lizhicai.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by lizhicai on 2017/10/22.
 */

public class ShowDetails extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout shopView = (LinearLayout)findViewById(R.id.shopList);
        FloatingActionButton btn = (FloatingActionButton)findViewById(R.id.car);
        shopView.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();

        final String showName = intent.getStringExtra("Name");
        final String showPrice = intent.getStringExtra("Price");
        final String showInfo = intent.getStringExtra("Info");

        DetailAdapter mAdapter;
        mRecyclerView = (RecyclerView) findViewById(R.id.goods);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new DetailAdapter(showName, showPrice, showInfo, ShowDetails.this, ShowDetails.this));
    }
}
