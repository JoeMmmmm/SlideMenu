package com.itheima.slidemenu96;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.menu_listview)
    ListView menuListview;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.main_listview)
    ListView mainListview;
    @Bind(R.id.slideMenu)
    SlideMenu slideMenu;

    //组件:一个拥有独立功能的模块就是一个组件
    //组件间通信:广播，接口回调，EventBus(观察者设计模式)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //填充数据
        menuListview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                , Constant.sCheeseStrings) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //偷梁换柱
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setTextColor(Color.WHITE);
                return view;
            }
        });

        mainListview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                , Constant.NAMES));

        //设置滑动改变的监听器
        slideMenu.setOnSlideChangeListener(new SlideMenu.OnSlideChangeListener() {
            @Override
            public void onOpen() {
                Toast.makeText(MainActivity.this,"芝麻开门",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onClose() {
                Toast.makeText(MainActivity.this,"关门大吉",Toast.LENGTH_SHORT).show();
                //使用平移的属性动画
                ViewCompat.animate(ivHead)
                          .translationXBy(100)
                          .setDuration(1000)
//                          .setInterpolator(new CycleInterpolator(14))
//                          .setInterpolator(new OvershootInterpolator(4))
                          .setInterpolator(new BounceInterpolator())//乒乓球落地
                          .start();
            }
            @Override
            public void onDraging(float fraction) {
//                Log.e("tag","fraction: "+fraction);
                ivHead.setRotation(720*fraction);
            }
        });

    }


}
