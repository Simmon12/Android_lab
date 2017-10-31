package com.example.lizhicai.shopapp;

/**
 * Created by lizhicai on 2017/10/30.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;
import static android.content.Context.NOTIFICATION_SERVICE;
/**
 * Created by lizhicai on 2017/10/30.
 */

public class StaticReceiver extends BroadcastReceiver {
    private static final String STATICACTION = "com.example.lizhicai.MyStaticFilter";
    private String name;
    private String price;
    private String info;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(STATICACTION)) {
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
            info = intent.getStringExtra("info");
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), getImage.MapImage(name));
            // 获取状态通知栏管理
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("新商品热卖")            // 设置通知标题栏
                    .setContentText(name+"仅售"+price)       // 设置通知栏显示内容
                    .setTicker("您有一条新信息")             // 通知首次出现在通知栏，会带上升动画效果
                    .setLargeIcon(bitmap).setSmallIcon(getImage.MapImage(name))   //设置通知大ICON和小ICON
                    .setAutoCancel(true);                    //当用户单击面板的时候，通知将自动取消
            Intent myIntent = new Intent(context, ShowDetails.class);   //定义一个intent
            myIntent.putExtra("Name", name);              // 将name，price，info传入intent中存储数据
            myIntent.putExtra("Price", price);
            myIntent.putExtra("Info", info);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setDefaults(Notification.DEFAULT_ALL);
            // 绑定notification，发送通知请求
            Notification notification = mBuilder.build();
            mNotificationManager.notify(1, mBuilder.build());
        }
    }
}
