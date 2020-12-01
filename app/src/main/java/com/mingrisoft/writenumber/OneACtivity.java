package com.mingrisoft.writenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

/**
 * Created by user on 2020/11/25.
 */

public class OneACtivity extends Activity {//OneACtivity头部
    private ImageView iv_frame;  //定义显示写数字的ImageView控件
    //igvx,igvy图片x,y坐标，
    // type：是否可以书写标识 ，
    // widthPixels,heightPixels屏幕宽高，

    int i=1,igvx,igvy,type=0,widthPixels,heightPixels;
    //按下时x，y值，屏幕移开时想，y值，移动中的x，y值
    // scaleWidth,scaleHeight宽高缩放比例，
    float x1,x2,x3,y1,y2,y3,scaleWidth,scaleHeight;
    // 单机虚拟按钮用于连续动作的计时器
    Timer touchTimer = null;
    //Bitmap图像处理
    Bitmap arrdown;
    //dialop对话框状态
    boolean typedialop = true;
    //linearLayout线性布局
    private LinearLayout linearLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        initView();
    }

    private void initView() {
        //获取显示写数字的ImageView控件
        iv_frame = (ImageView)findViewById(R.id.iv_frame);
        //获取数字区域的布局
        linearLayout = (LinearLayout)findViewById(R.id.LinearLayout1);
        //获取书写界面布局
        LinearLayout write_layout = (LinearLayout)findViewById(R.id.LinearLayout_number);
        //设置书写界面布局背景
        write_layout.setBackgroundResource(R.drawable.bg1);
        //获取屏幕宽度
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        //获取屏幕高度
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        //图片是1280*720，若是其他分辨率，做适应处理

        scaleWidth = ((float) widthPixels / 720);
        scaleHeight = ((float) heightPixels / 1280);
        try {
            //通过输入流打开第一个照片
            InputStream is = getResources().getAssets().open("on1_1.png");
            //使用Bitmap解析第一张图片
            arrdown = BitmapFactory.decodeStream(is);
        }catch (IOException e){
            e.printStackTrace();
        }

        //获取布局的宽高信息
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_frame.getLayoutParams();
        //获取图片缩放后的高度
        layoutParams.width = (int) (arrdown.getWidth() * scaleWidth);
        //获取图片缩放后的高度
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);

        //根据图片缩放后的宽高，设置iv_frame的宽高
        iv_frame.setLayoutParams(layoutParams);
        lodimagep(1);//调用lodimagep，进入页面后加载第一张图片

        linearLayout.setOnTouchListener(new View.OnTouchListener() { //设置手势判断事件
            @Override
            public boolean onTouch(View v, MotionEvent event) { //手势按下判断的OnTouch()方法
                switch (event.getAction()){  //获取行动方式
                    case MotionEvent.ACTION_DOWN:  //手指按下事件
                        //获取手指按下的坐标
                        x1 = event.getX();
                        y1 = event.getY();
                        igvx = iv_frame.getLeft(); //获取手指按下时图片x坐标
                        igvy = iv_frame.getTop();  //获取手指按下时图片y坐标
                        //当手指坐标大于图片时，说明手指在移动，开启书写
                        if(x1 > igvx && x1 < igvx + (int)(arrdown.getWidth() * scaleWidth) && y1 > igvy && y1 < (arrdown.getHeight() * scaleHeight)){
                            type = 1;  //开启书写
                        }else {
                            type = 0;  //关闭书写
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:		// 手势移动中判断
                        igvx = iv_frame.getLeft(); 		// 获取图片的X坐标
                        igvy = iv_frame.getTop(); 			// 获取图片的Y坐标
                        x2 = event.getX(); 				// 获取移动中手指在屏幕X坐标的位置
                        y2 = event.getY(); 				// 获取移动中手指在屏幕Y坐标的位置
                        // 下边 是根据比划 以及 手势 做图片的处理 滑动到不同位置 加载不同图片
                        if (type == 1) { 					// 如果书写开启
                            // 如果手指按下的X坐标大于等于图片的X坐标，或者小于等于缩放图片的X坐标时
                            if (x2 >= igvx && x2 <= igvx + (int) (arrdown.getWidth() * scaleWidth)) {
                                System.out.println(x2);System.out.println(igvx);
                                System.out.println(igvx + (int) (arrdown.getWidth() * scaleWidth));
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标，或者大于等于图片的Y坐标时
                                if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 && y2 >= igvy) {
                                    lodimagep(1);      		// 调用lodimagep()方法，加载第一张显示图片
                                }
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 2) {
                                    lodimagep(2);            // 调用lodimagep()方法，加载第二张显示图片
                                }
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 3) {
                                    lodimagep(3);            // 调用lodimagep()方法，加载第三张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 4) {
                                    lodimagep(4);            // 调用lodimagep()方法，加载第四张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 5) {
                                    lodimagep(5);            // 调用lodimagep()方法，加载第五张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 6) {
                                    lodimagep(6);            // 调用lodimagep()方法，加载第六张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 7) {
                                    lodimagep(7);            // 调用lodimagep()方法，加载第七张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 8) {
                                    lodimagep(8);            // 调用lodimagep()方法，加载第八张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 9) {
                                    lodimagep(9);            // 调用lodimagep()方法，加载第九张显示图片
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 10) {
                                    lodimagep(10);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 11) {
                                    lodimagep(11);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 12) {
                                    lodimagep(12);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 13) {
                                    lodimagep(13);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 14) {
                                    lodimagep(14);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 15) {
                                    lodimagep(15);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 16) {
                                    lodimagep(16);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 17) {
                                    lodimagep(17);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 18) {
                                    lodimagep(18);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 19) {
                                    lodimagep(19);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 20) {
                                    lodimagep(20);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 21) {
                                    lodimagep(21);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 22) {
                                    lodimagep(22);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 23) {
                                    lodimagep(23);
                                }
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 24) {
                                    lodimagep(24);   //加载最后一张图片时，将在lodimagep()方法中调用书写完成对话框
                                }
                                else {
                                    type = 0;         // 手指离开 设置书写关闭
                                }

                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private synchronized void lodimagep(int j) {
            i = j;
        if (i<25){
            String name = "on1_" + i ;
            //获取图片资源id
            int imgid = getResources().getIdentifier(name, "drawable", "com.mingrisoft.writenumber");
                //System.out.println(imgid);
                iv_frame.setBackgroundResource(imgid);
                i++;
        }if (j == 24){
            if (typedialop){
                iadlog();
            }
        }
    }

    private void iadlog() {  //完成后提示对话框
        typedialop = false;
        //实例化对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(OneACtivity.this);
        builder.setMessage("太棒了！书写完成！");
        builder.setTitle("提示");
        //设置对话框完成按钮点击事件
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //dialog消失
                typedialop = true;  //修改对话框状态
                finish();            //关闭当前面页
            }
        });
//        设置对话框再来一次按钮点击事件
        builder.setNegativeButton("再来一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                typedialop = true;
                i = 1;
                lodimagep(i);
            }
        });
        builder.create().show();   //创建并显示对话框
    }
}//OneACtivity尾部
