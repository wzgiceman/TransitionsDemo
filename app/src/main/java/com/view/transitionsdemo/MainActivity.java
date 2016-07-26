package com.view.transitionsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    //    Slide （滑行）
    @OnClick(R.id.btn_fade)
    public void onFadeClick() {
        Intent intent = new Intent(this, SlideActivity.class);
        startActivity(intent);
    }

    //    扩散
    @OnClick(R.id.btn_explode)
    public void onExploadClick() {
        Intent intent = new Intent(this, ExplodeActivity.class);
        startActivity(intent);
    }

    //    隐藏和大小变化
    @OnClick(R.id.btn_fade_scale)
    public void onFadeScaleClick() {
        Intent intent = new Intent(this, ScaleAndFadeActivity.class);
        startActivity(intent);
    }

    //    路径变化
    @OnClick(R.id.btn_path)
    public void onPathClick() {
        Intent intent = new Intent(this, PathActivity.class);
        startActivity(intent);
    }

    //    文字相关
    @OnClick(R.id.btn_tx)
    public void ontvClick() {
        Intent intent = new Intent(this, TxtActivity.class);
        startActivity(intent);
    }
}
