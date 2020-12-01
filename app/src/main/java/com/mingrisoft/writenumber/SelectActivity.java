package com.mingrisoft.writenumber;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends Activity {
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        if(MainActivity.isPlay == true){
            PlayMusic();
        }
    }
    private void  PlayMusic(){   //播放背景音乐
        mediaplayer = MediaPlayer.create(this, R.raw.number_music);
        mediaplayer.isLooping();
        mediaplayer.start();
    }
    //选择数字界面停止时，停止播放音乐
    protected void  onStop(){
        super.onStop();  //表示立即执行
        if(mediaplayer != null){
            mediaplayer.stop();
        }
    }
    //当选择数字界面销毁时，停止播放音乐并释放音乐资源
    protected void  onDestroy(){
        super.onDestroy();
        if(mediaplayer != null){
            mediaplayer.stop();  //停止
            mediaplayer.release();  //释放音乐资源
            mediaplayer = null; //播放器为空

        }
    }

    //从另一界面返回数字界面时，根据音乐播放状态播放
    protected void  onRestart(){
        super.onRestart();
        if(MainActivity.isPlay == true){
            PlayMusic();
        }
    }

    public void OnOne(View v){
            startActivity(new Intent(SelectActivity.this, OneACtivity.class));
    }
}
