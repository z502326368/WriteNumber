package com.mingrisoft.writenumber;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity { //MainActivity头部
    static boolean isPlay = true;//设置音乐播放状态
    MediaPlayer mediaplayer;
    Button music_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取布局文件控制音乐背景按钮
        music_btn = (Button) findViewById(R.id.btn_music);
        PlayMusic();
    }//onCreate方法尾部

    public void Onplay(View v){ //单击事件，进入选择数字界面
        //当前界面跳转至选择数字界面
        startActivity(new Intent(MainActivity.this, SelectActivity.class));
    }

    public void OnAbout(View v){
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }

    public void PlayMusic(){
        //创建音乐播放器对象，并加载播放音乐
        mediaplayer=MediaPlayer.create(this,R.raw.main_music);
        mediaplayer.setLooping(true);//循环播放
        mediaplayer.start();//开始播放音乐
    }
    //判断是播放还是停止播放
    public void OnMusic(View v){
        if(isPlay == true){   //音乐处于播放状态
            if (mediaplayer != null){   //音乐播放器不为空
                mediaplayer.stop();    //停止播放音乐
                //改变按钮为停止播放音乐的按钮
                music_btn.setBackgroundResource(R.drawable.btn_music2);
                isPlay = false;
            }
        }else  {
            PlayMusic();
            music_btn.setBackgroundResource(R.drawable.btn_music1);
            isPlay = true;
        }
    }
}//MainActivity尾部
