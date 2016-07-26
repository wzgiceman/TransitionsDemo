package com.view.transitionsdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * txt常用动画demo
 */
public class TxtActivity extends AppCompatActivity {

    @BindView(R.id.btn_color)
    Button btnColor;
    @BindView(R.id.btn_txt)
    Button btnTxt;
    @BindView(R.id.btn_rote)
    Button btnRote;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.llyout)
    LinearLayout llyout;
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt);
        ButterKnife.bind(this);
    }

    boolean colorTag;
    boolean txTag;
    boolean roteTag;


    @OnClick({R.id.btn_color, R.id.btn_txt, R.id.btn_rote})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_color:
                colorTag = !colorTag;
                TransitionManager.beginDelayedTransition(llyout, new Recolor().setDuration(1000));
                if (colorTag) {
                    tv.setTextColor(getResources().getColor(R.color.drak));
                    tv.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tv.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.drak)));
                }
                break;
            case R.id.btn_txt:
                TransitionManager.beginDelayedTransition(llyout, new ChangeText().setChangeBehavior(ChangeText
                        .CHANGE_BEHAVIOR_OUT_IN).setDuration(1000));
                txTag = !txTag;
                if (txTag) {
                    tv.setText("文字我改了");
                } else {
                    tv.setText("文字又复原了");
                }
                break;
            case R.id.btn_rote:
                TransitionManager.beginDelayedTransition(llyout, new Rotate().setDuration(1000));
                roteTag = !roteTag;
                if (roteTag) {
                    img.setRotation(90);
                    tv.setRotation(180);
                } else {
                    img.setRotation(0);
                    tv.setRotation(0);
                }
                break;
        }
    }
}
