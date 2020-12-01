package com.mingrisoft.writenumber;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends Activity { //StartActivity类头部

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate（）方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Timer timer=new Timer();//创建Timer对象，用于设置启动界面的时间
        //创建TimerTask对象，用于启动界面向主界面跳转
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                //从启动界面跳转到主界面
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish(); //关闭启动界面
            }
        };
        timer.schedule(timerTask,2000); //定时器，延时执行
    } //onCreate（）方法尾部
} //StartActivity类尾部
