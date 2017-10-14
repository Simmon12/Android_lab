package com.example.lizhicai.login23;

import android.content.DialogInterface;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage;
    private Button login, regist, checked;
    private RadioGroup mRadioGroup;
    private RadioButton RB1, RB2;
    private TextInputLayout mIdText, mPassWord;
    private String  Rchecked;
    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = (ImageView) findViewById(R.id.image);
        mImage.setOnClickListener(new View.OnClickListener() {
            final String[] items = new String[]{"拍摄","从相册中选择"};
            @Override
            public void onClick(View v) {
                // 创建一个对话框对象
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                // 对对话框内容进行定义
                builder.setTitle("上传头像").setItems(
                        items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Toast.makeText(MainActivity.this, "您选择了[拍摄]", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(MainActivity.this, "您选择了[从相册选择]", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }
                ).setNegativeButton(
                        "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "“您选择了[取消]", Toast.LENGTH_SHORT ).show();
                            }
                        }).show();
            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        RB1 = (RadioButton) findViewById((R.id.student));
        RB2 = (RadioButton) findViewById((R.id.teacher));
        checked = (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
        Rchecked = checked.getText().toString();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == RB1.getId()) {
                    checked =  (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
                    Rchecked = checked.getText().toString();
                    Snackbar.make(group, "您选择了学生", Snackbar.LENGTH_INDEFINITE)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
                else if(checkedId == RB2.getId()) {
                    checked =  (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
                    Rchecked = checked.getText().toString();
                    Snackbar.make(group, "您选择了教职工", Snackbar.LENGTH_INDEFINITE)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });

        mIdText = (TextInputLayout) findViewById(R.id.student_id);
        mPassWord = (TextInputLayout) findViewById(R.id.student_pwd);
        login = (Button) findViewById(R.id.login);
        regist = (Button) findViewById((R.id.register));
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mIdText.getEditText().getText().toString();
                String pwd = mPassWord.getEditText().getText().toString();
                if(id.length() == 0 || pwd.length() == 0) {
                    if(id.length()==0) {
                        mIdText.setErrorEnabled(true);
                        mIdText.setError("学号不能为空");
                    }
                    if (pwd.length()==0) {
                        mPassWord.setErrorEnabled(true);
                        mPassWord.setError(("密码不能为空"));
                    }
                    if(id.length()!=0) {
                        mIdText.setErrorEnabled(false);
                    }
                    if(pwd.length()!=0) {
                        mPassWord.setErrorEnabled(false);
                    }
                }
                else {
                    if (id.equals("123456") && pwd.equals("6666")) {
                        Snackbar.make(rootView, "登录成功", Snackbar.LENGTH_INDEFINITE
                        ).setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                    }
                    if (!id.equals("123456") || !pwd.equals("6666")){
                        Snackbar.make(rootView, "学号或密码错误", Snackbar.LENGTH_INDEFINITE
                        ).setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                    }
                }


            }
        });

         regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if( Rchecked.equals("学生")) {
                      Toast.makeText(MainActivity.this, "学生注册功能尚未启用", Toast.LENGTH_SHORT).show();
                  } else if (Rchecked.equals("教职工")) {
                      Toast.makeText(MainActivity.this, "教职工注册功能尚未启用", Toast.LENGTH_SHORT).show();
                  }
            }
        });



    }
}
