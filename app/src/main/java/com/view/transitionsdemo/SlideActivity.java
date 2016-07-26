package com.view.transitionsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transitionseverywhere.Slide;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 滑动动画demo界面
 */
public class SlideActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.llyout)
    LinearLayout llyout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_fade_left, R.id.btn_fade_right, R.id.btn_fade_top, R.id.btn_fade_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fade_left:
                onFadeClick(Gravity.LEFT);
                break;
            case R.id.btn_fade_right:
                onFadeClick(Gravity.RIGHT);
                break;
            case R.id.btn_fade_top:
                onFadeClick(Gravity.TOP);
                break;
            case R.id.btn_fade_bottom:
                onFadeClick(Gravity.BOTTOM);
                break;
        }
    }


    //    Slide （滑行）
    public void onFadeClick(int diction) {
        Transition transition = new Slide(diction);
        TransitionManager.beginDelayedTransition(llyout, transition);
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}
