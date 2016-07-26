package com.view.transitionsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScaleAndFadeActivity extends AppCompatActivity {

    @BindView(R.id.btn_fade)
    Button btnFade;
    @BindView(R.id.btn_scale)
    Button btnScale;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.llyout)
    LinearLayout llyout;
    @BindView(R.id.btn_scale_fade)
    Button btnScaleFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_and_fade);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_fade)
    public void onFadeClick() {
        Transition transition=new Fade();
        transition.setDuration(2000);
        TransitionManager.beginDelayedTransition(llyout,transition );
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.btn_scale_fade)
    public void onAllClick() {
        TransitionSet set = new TransitionSet()
                .addTransition(new Scale(0.9f))
                .addTransition(new Fade());
        set.setDuration(2000);
        TransitionManager.beginDelayedTransition(llyout, set);
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.btn_scale)
    public void onScaleClick() {
        Transition transition=new Scale(0.8f);
        transition.setDuration(2000);
        TransitionManager.beginDelayedTransition(llyout, transition);
        tv.setVisibility(tv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}
