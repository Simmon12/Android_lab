package com.example.lizhicai.musicbox;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {
    private TextView status, start_time, end_time;
    private Button play, quit, stop;
    private Integer flag;
    private SeekBar seekBar;
    private Boolean is_changing_seek;
    private ImageView pic;
    private IBinder myBinder;
    private ServiceConnection sc;
    private SimpleDateFormat mysdf = new SimpleDateFormat("mm:ss");
    private ObjectAnimator myAnimator;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Click_Listener();
        initServer();
        initHandle();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        flag = -1;
        is_changing_seek = false;
        status = (TextView)findViewById(R.id.status);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        start_time = (TextView)findViewById(R.id.start);
        end_time = (TextView)findViewById(R.id.end);
        play = (Button)findViewById(R.id.play);
        quit = (Button)findViewById(R.id.quit);
        stop = (Button)findViewById(R.id.stop);
        pic = (ImageView)findViewById(R.id.pic);

        myAnimator = ObjectAnimator.ofFloat(pic, "rotation", 0, 360);
        myAnimator.setDuration(20000);
        myAnimator.setInterpolator(new LinearInterpolator());
        myAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        myAnimator.setRepeatMode(ObjectAnimator.RESTART);
        myAnimator.start();
        myAnimator.pause();
    }

    private void Click_Listener() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Parcel reply = Parcel.obtain();
                    myBinder.transact(0, null, reply, 0);
                    flag = reply.readInt();
                    refreshStatus();
            }catch (Exception e) {
                e.printStackTrace();
            }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                try {
                    Parcel reply = Parcel.obtain();
                    myBinder.transact(1, null, reply, 0);
                    flag = reply.readInt();
                    refreshStatus();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onDestroy();
                finish();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                start_time.setText(mysdf.format(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                is_changing_seek = true;
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Parcel data = Parcel.obtain();
                    data.writeInt(seekBar.getProgress());
                    myBinder.transact(4, data, null, 0);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                is_changing_seek = false;
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void refreshStatus() throws RemoteException {
        switch (flag) {
            case 0: // play
                play.setText("PAUSE");
                status.setText("playing");
                if(myAnimator.isStarted()) {
                    myAnimator.resume();
                } else
                    myAnimator.start();
                break;
            case 1:   // pause
                play.setText("PLAY");
                status.setText("pausing");
                myAnimator.pause();
                break;
            case 2:  // // STOP
                play.setText("PLAY");
                status.setText("stopped");
                myAnimator.pause();
                myAnimator.end();
                break;
        }
    }

    private void initServer() {
        sc = new ServiceConnection() {   //ServiceConnection 实例化
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = service;
                try {
                    Parcel reply = Parcel.obtain();
                    myBinder.transact(5, null, reply, 0);   // get the duration of the song
                    int timeDuration = reply.readInt();
                    seekBar.setMax(timeDuration);
                    end_time.setText(mysdf.format(timeDuration));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                sc = null;

            }
        };
        Intent intent = new Intent(this, MusicServer.class);
        startService(intent);   // 开启服务
        bindService(intent, sc, BIND_AUTO_CREATE);  // 绑定activity和服务
    }

    private void initHandle() {
        final Handler refresher = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(!is_changing_seek) {
                    try {
                        Parcel reply = Parcel.obtain();
                        myBinder.transact(3, null, reply, 0);
                        seekBar.setProgress(reply.readInt());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.handleMessage(msg);
            }
        };
        Thread t_refresher = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (sc != null) {
                        refresher.obtainMessage().sendToTarget();
                    }
                }
            }
        };
        t_refresher.start();

    }

    @Override
    public void finish() {
        try {
            myBinder.transact(2, null, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        unbindService(sc);
        sc = null;
//        super.onDestroy();
        super.finish();
    }




}
