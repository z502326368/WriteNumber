package com.mingrisoft.writenumber;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SelectActivity extends Activity {  //SelectActivity类头部
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        if(MainActivity.isPlay == true){
            PlayMusic();
        }
    }  //onCreate方法结尾
    public void PlayMusic(){
        mediaPlayer = MediaPlayer.create(this, R.raw.number_music);
        mediaPlayer.isLooping();
        mediaPlayer.start();
    }

//    实现选择数字界面停止时，音乐停止
    protected void onStop(){
        super.onStop();
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

//    实现选择数字界面销毁时，背景音乐停止并释放音乐资源
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();  //停止播放音乐
            mediaPlayer.release(); //释放音乐资源
            mediaPlayer = null; //设置音乐播放器为空
        }
    }
//从另外一界面返回选择数字界面时，判断是否需要播放音乐
    protected void onRestart(){
        super.onRestart();
        if (MainActivity.isPlay == true){
            PlayMusic();
        }
    }
} //SelectActivity类结尾
