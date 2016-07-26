package com.view.transitionsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PathActivity extends AppCompatActivity {

    @BindView(R.id.btn_path)
    Button btnPath;
    @BindView(R.id.rlyout)
    FrameLayout rlyout;
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.llyout_data)
    LinearLayout llyoutData;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        ButterKnife.bind(this);

        for (int i = 0; i < 10; i++) {
            list.add("str" + i);
        }
    }

    boolean open;

    @OnClick(R.id.btn_path)
    public void onClick() {
        TransitionManager.beginDelayedTransition(rlyout,
                new ChangeBounds().setDuration(1000));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) btnPath.getLayoutParams();
        params.gravity = open ? Gravity.TOP : Gravity.BOTTOM;
        btnPath.setLayoutParams(params);
        open = !open;
    }

    @OnClick(R.id.btn_change)
    public void onChangeClick() {
        TransitionManager.beginDelayedTransition(llyoutData, new ChangeBounds().setDuration(1000));
        llyoutData.removeAllViews();
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            TextView tv =(TextView) View.inflate(this, R.layout.llyout_item, null);
            tv.setText(item);
            TransitionManager.setTransitionName(tv, item);
            llyoutData.addView(tv);
        }
    }

}
