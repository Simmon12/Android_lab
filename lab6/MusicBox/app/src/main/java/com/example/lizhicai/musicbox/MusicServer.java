package com.example.lizhicai.musicbox;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * Created by lizhicai on 2017/11/28.
 */

public class MusicServer extends Service{
    private IBinder myBinder;

    static MediaPlayer mediaPlayer;
    public MusicServer() {
        myBinder = new MyBinder();
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource("/data/melt.mp3");
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    private class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 0:                // 播放按钮，服务处理函数
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        reply.writeInt(1);
                    } else  {
                        mediaPlayer.start();
                        reply.writeInt(0);
                    }
                    break;
                case 1:                 // 停止按钮，服务处理函数
                    try {
                        mediaPlayer.stop();
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    reply.writeInt(2);
                    break;
                case 2:                 // 退出按钮，服务处理函数
                    onDestroy();
                    break;
                case 3:                  // 界面刷新，返回当前时间点
                    reply.writeInt(mediaPlayer.getCurrentPosition());
                    break;
                case 4:                   // 拖动进度条，服务处理函数
                    mediaPlayer.seekTo(data.readInt());
                    break;
                case 5:                   // 获取整首歌曲的时间
                    reply.writeInt(mediaPlayer.getDuration());
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

}
