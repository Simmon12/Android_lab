package com.example.lizhicai.shopapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ListView mListView;

    private List<Goods>good;
    private List<Goods>shopCarGood;

    private goodsAdapter mgoodsAdapter;
     private  shopCarAdapter mshopCarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout shopCartView = (LinearLayout) findViewById(R.id.shopList);
        shopCartView.setVisibility(View.INVISIBLE);

        final String[] Name = new String[]{"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
        final String[] Price = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.00", "¥ 132.59", "¥ 141.43", "139.43", "28.90"};
        final String[] Info = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};

        good = new ArrayList<Goods>();
        shopCarGood = new ArrayList<Goods>();
        for(int i = 0; i < Name.length; i++) {
            good.add(new Goods(Name[i], Info[i], Price[i]));
        }

        mshopCarAdapter = new shopCarAdapter(MainActivity.this, shopCarGood);
        mRecyclerView = (RecyclerView) findViewById(R.id.goods);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mgoodsAdapter = new goodsAdapter(good, MainActivity.this);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mgoodsAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
        mgoodsAdapter.setmOnItemClickListener(new goodsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                intent.putExtra("Name", good.get(position).getName());
                intent.putExtra("Price", good.get(position).getPrice());
                intent.putExtra("Info", good.get(position).getInfo());
                startActivityForResult(intent,0);
            }
            @Override
            public void onLongClick(int position) {
                Toast.makeText(MainActivity.this,"移除第"+String.valueOf(position+1)+"个商品",Toast.LENGTH_SHORT).show();
                good.remove(position);
                mgoodsAdapter.notifyDataSetChanged();
            }
        });

        mListView = (ListView) findViewById(R.id.shopCarItem);
        mListView.setAdapter(mshopCarAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, final int i,long l) {
                Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                intent.putExtra("Name", shopCarGood.get(i).getName());
                intent.putExtra("Price", shopCarGood.get(i).getPrice());
                intent.putExtra("Info", shopCarGood.get(i).getInfo());
                startActivityForResult(intent,0);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品").setMessage("从购物车移除" + shopCarGood.get(i).getName()+ "?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if(shopCarGood.remove(i) != null) {
                                    mshopCarAdapter.notifyDataSetChanged();
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "你选择了取消", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });

        final ImageButton change = (ImageButton) findViewById(R.id.car);
        change.setTag("0");
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change.getTag() == "0") {
                    change.setImageResource(R.drawable.mainpage);
                    change.setTag("1");
                    shopCartView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                } else {
                    change.setImageResource(R.drawable.shoplist);
                    change.setTag("0");
                    shopCartView.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0 && resultCode == 0) {
            Bundle bud = intent.getExtras();
            String str = bud.getString("name");
            String pri = bud.getString("Price");
            String info = bud.getString("info");
            int cnt = bud.getInt("cnt", 0);
            for(int i = 0; i < cnt; i++) {
                shopCarGood.add(new Goods(str, info, pri));
            }
            mshopCarAdapter.notifyDataSetChanged();

        }
    }
}
