package com.example.lizhicai.datastore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences myPassSp;
    private SharedPreferences.Editor mspfEditor;
    private Button btn_ok,btn_clear, btn_reg;
    private EditText pw1, pw2;
    private boolean is_registered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        EventListening();
    }
    public void init() {
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_reg = (Button)findViewById(R.id.btn_reg);
        pw1 = (EditText)findViewById(R.id.pw1);
        pw2 = (EditText)findViewById(R.id.pw2);

    }
    public void EventListening() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                String pw1_str = pw1.getText().toString();
                if(!is_registered) {
                    String pw2_str = pw2.getText().toString();   // 获取控件pw2处的内容
                    if(pw1_str.isEmpty()) {                      // 当控件pw1的内容为空时
                        msg = "Password cannot be empty!";   // 进行相应的提示
                    }
                    else if(pw2_str.isEmpty() || !pw2_str.equals(pw1_str)) {  //如果两个密码框中的内容不一致
                        msg = "Confirm password miss match!";             // 提示不匹配，重新输入
                    }
                    else {                                         // 来到这表示两次输入的密码已经一致，
                        msg = "Register successful";            // 注册成功，将密码保存进SharedPreferences
                        mspfEditor.putString("password", pw1_str);
                        mspfEditor.commit();
                        Intent mIntent = new Intent(MainActivity.this, EditActivity.class);
                        startActivity(mIntent);
                    }
                }
                else {
                    String repeatPW = myPassSp.getString("password", null);
                    if (repeatPW.equals(pw1_str)) {
                        Intent mIntent = new Intent(MainActivity.this, EditActivity.class);
                        startActivity(mIntent);
                        msg = "Logging successful";
                    }
                    else {
                        msg = "Wrong Password";
                    }
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw1.setText("");
                pw2.setText("");
                Toast.makeText(getApplicationContext(),"clear", Toast.LENGTH_SHORT).show();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_registered = false;
                pw2.setVisibility(View.VISIBLE);
                pw1.setHint("New Password");
                pw2.setHint("Confirm Password");
                Toast.makeText(getApplicationContext(),"Register again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected  void onResume() {
        super.onResume();
        myPassSp = getApplicationContext()
                .getSharedPreferences("password", Context.MODE_PRIVATE);   // 从sharePreferences中获取密码
        mspfEditor=myPassSp.edit();          // 获取sharePreferences配套的编辑器
        if(myPassSp.getString("password", null) != null) {  // 如果密码不为空，说明之前已经成功注册了
            is_registered = true;                         // 所以这时，这时只需要输入密码，将注册状态变为true
            pw2.setVisibility(View.INVISIBLE);            // 隐藏pw2的输入框
            pw1.setHint("Enter your password");
            pw1.setText("");
        }
    }
}
