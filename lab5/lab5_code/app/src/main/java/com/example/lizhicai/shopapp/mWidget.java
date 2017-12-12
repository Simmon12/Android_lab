package com.example.lizhicai.shopapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class mWidget extends AppWidgetProvider {
    private static final String DYNAMICACTION = "com.example.lizhicai.MyWidgetDynamicFilter";
    private static final String STATICACTION = "com.example.lizhicai.MyWidgetStaticFilter";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        Intent  myIntent = new Intent(context, MainActivity.class);
        PendingIntent myPendingIntent = PendingIntent.getActivity(context,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.appwidget, myPendingIntent);
        ComponentName me = new ComponentName(context, mWidget.class);
        appWidgetManager.updateAppWidget(me, updateViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        if(intent.getAction().equals(STATICACTION)) {
            String name = intent.getStringExtra("name");
            String price = intent.getStringExtra("price");
            String info = intent.getStringExtra("info");
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), getImage.MapImage(name));
            Intent myIntent = new Intent(context, ShowDetails.class);   //定义一个intent
            myIntent.putExtra("Name", name);              // 将name，price，info传入intent中存储数据
            myIntent.putExtra("Price", price);
            myIntent.putExtra("Info", info);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            updateViews.setTextViewText(R.id.appwidget_text, name+"仅售"+price+"!");
            updateViews.setImageViewResource(R.id.appwidget_image, getImage.MapImage(name));
            updateViews.setOnClickPendingIntent(R.id.appwidget, pendingIntent);
            ComponentName componet = new ComponentName(context, mWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componet, updateViews);
        } else if(intent.getAction().equals(DYNAMICACTION)) {
            Toast.makeText(context,"widgetDongtai",Toast.LENGTH_SHORT).show();
            String name = intent.getStringExtra("name");
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), getImage.MapImage(name));
            Intent myIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,1,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setTextViewText(R.id.appwidget_text, name+"已添加到购物车");
            updateViews.setImageViewResource(R.id.appwidget_image, getImage.MapImage(name));
            updateViews.setOnClickPendingIntent(R.id.appwidget, pendingIntent);
            ComponentName componet = new ComponentName(context, mWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componet, updateViews);
        }
    }
}

