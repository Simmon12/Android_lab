package com.example.lizhicai.shopapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by lizhicai on 2017/10/30.
 */

public class DynamicReceiver extends BroadcastReceiver {
    private String name;
    private static final String DYNAMICACTION = "com.example.lizhicai.MyDynamicFilter";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(DYNAMICACTION)) {
            Toast.makeText(context,"动态广播",Toast.LENGTH_SHORT).show();
            name = intent.getStringExtra("name");
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), getImage.MapImage(name));
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent myIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,1,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("马上下单")
                    .setContentText(name+"已添加到购物车")
                    .setLargeIcon(bitmap)
                    .setSmallIcon(getImage.MapImage(name))
                    .setTicker("您有一条新信息")
                    .setAutoCancel(true);
            mBuilder.setContentIntent(pendingIntent);
//            mBuilder.setContent(contentView);
            mBuilder.setDefaults(Notification.DEFAULT_ALL);
            Notification notification = mBuilder.build();
            mNotificationManager.notify(2, mBuilder.build());
        }
    }
}
