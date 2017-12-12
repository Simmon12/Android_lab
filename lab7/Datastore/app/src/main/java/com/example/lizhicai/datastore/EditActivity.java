package com.example.lizhicai.datastore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lizhicai on 2017/12/10.
 */
public class EditActivity extends AppCompatActivity{
    private Button btn_save, clear, btn_load, btn_delete;
    private EditText ed_title, ed_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        EventListening();
    }
    private void init() {
        btn_save = (Button)findViewById(R.id.btn_save);
        clear = (Button) findViewById(R.id.clear);
        btn_load = (Button) findViewById(R.id.btn_load);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        ed_title = (EditText) findViewById(R.id.edit_title);
        ed_content = (EditText) findViewById(R.id.edit_content);
    }
    private  void EventListening() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                String title = ed_title.getText().toString();
                String content = ed_content.getText().toString();
                if(title.isEmpty()) {
                    msg = "Title should not be empty";
                }else {
                    try {
                        FileOutputStream localFileOutputStream =   // 创建一名为title(变量)的文件，并返回
                                getApplicationContext().openFileOutput(title, 0); //一个FileOutpuStream对象
                        localFileOutputStream.write(content.getBytes());  // 将编辑框中的字符串转化为bytes，并写入上面创建的文件中
                        localFileOutputStream.close();
                        msg = "save successfully";

                    } catch (IOException e) {
                        msg = "save fail";
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_content.setText("");
            }
        });

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                String title = ed_title.getText().toString();
                if(title.isEmpty()) {
                    msg = "Title shoud not be empty";
                } else {
                    try {
                        // 使用OpenFileInput函数读取文件title，并返回一个FileInputStream对象
                        FileInputStream localFile = getApplicationContext().openFileInput(title);
                        // 文件中的内容是以byte格式存储，所以需要一个byte类型的中间变量content
                        byte[] content = new byte[localFile.available()];
                        localFile.read(content); // 调用FileInputStream对象中的read函数读取文件的内容
                        localFile.close();
                        ed_content.setText(new String(content)); // 将content转化为Sting，并写入编辑框中
                        msg = "load from file successfully";
                    }catch (IOException e ){
                        msg = "No such file";
                        ed_content.setText("");
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                String title = ed_title.getText().toString();
                if(title.isEmpty()) {
                    msg = "Please enter your file name first";
                }else {
                    deleteFile(title);  // 删除名为title的文件
                    ed_title.setText("");
                    ed_content.setText("");
                    msg = "Delete successfully";
                }
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(mHomeIntent);
    }

}
