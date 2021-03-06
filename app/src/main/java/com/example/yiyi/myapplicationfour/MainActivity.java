package com.example.yiyi.myapplicationfour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

// 内部存储: Android系统 允许应用程序创建仅能够自身访问的私有文件，文件保存在设备的内部存储器上
// 在Android系统下的/data/data/<package name>/files目录中
// Android系统不仅支持标准Java的IO类和方法，还提供了能够简化读写流式文件过程的函数
// openFileOutput()  openFileInput()


public class MainActivity extends AppCompatActivity {

    private final static String MyFileName="myfile"; //文件

    private RadioGroup radioGroup;
    private RadioButton aaaButton;
    private RadioButton bbbButton;

    private String abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // 以下单选；
        //获取实例
        radioGroup = (RadioGroup)findViewById(R.id.radioGroupID);
        aaaButton = (RadioButton)findViewById(R.id.aaaID);
        bbbButton = (RadioButton)findViewById(R.id.bbbID);
        //设置监听
        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());


        Button btnWrite = (Button)findViewById(R.id.button);
        Button btnRead = (Button)findViewById(R.id.button2);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream out=null;

                try {
                    //openFileOutput()函数为 写入数据做准备 而打开文件 (文件名称，4中文件操作模式)
                    FileOutputStream fileOutputStream = openFileOutput(MyFileName,MODE_PRIVATE);
                    //使用openFileOutput()获得FileOutputStream对象，
                    //    mode为MODE_PRIVATE，则文件不存在时创建文件，文件存在时删除文件内容

                    out = new BufferedOutputStream(fileOutputStream);

                    EditText editText = (EditText)findViewById(R.id.textOne);
                    String content = editText.getText().toString();

                    // String content = "2014011191";

                    //通过 FileOutputStream对象的write()方法 读取数据
                    try {
                        out.write(content.getBytes(StandardCarsets.UTF_8));
                    }
                    finally {
                        if(out!=null)
                            out.close();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream in=null;
                try {
                    //openFileInput()函数为 读取数据做准备 而打开文件 (文件名称)
                    FileInputStream fileInputStream = openFileInput(MyFileName);
                    in = new BufferedInputStream(fileInputStream);

                    int c;
                    StringBuilder stringBuilder = new StringBuilder("");

                    try{
                        while ((c=in.read()) != -1) {
                            stringBuilder.append((char)c);
                        }
                        Toast.makeText(MainActivity.this,abc + " " + stringBuilder.toString(), Toast.LENGTH_LONG).show();
                    }
                    finally {
                        if(in != null)
                            in.close();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


    }

    //定义一个RadioGroup的OnCheckedChangeListener
    //RadioGroup  绑定的是RadioGroup.OnCheckedChangeListener
    //CheckBox    绑定的是CompoundButton.OnCheckedChangeListener 或者 view.OnClickListener
    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId==aaaButton.getId()){
                //System.out.println("选中了aaa!");
                abc = "aaa";
            }else if (checkedId==bbbButton.getId()){
                //System.out.println("选中了bbb!");
                abc = "bbb";
            }
        }
    }



}
