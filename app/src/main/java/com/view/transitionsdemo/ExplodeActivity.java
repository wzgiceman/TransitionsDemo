package com.view.transitionsdemo;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 例子扩散界面demo
 */
public class ExplodeActivity extends AppCompatActivity {

    @BindView(R.id.recyle)
    EasyRecyclerView recyle;
    List<View> list = new ArrayList<>();
    BannerAdapter adapter;
    @BindView(R.id.btn_close)
    Button btnClose;
    @BindView(R.id.rlyout)
    RelativeLayout rlyout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explode);
        ButterKnife.bind(this);
        adapter = new BannerAdapter(this);
        recyle.setLayoutManager(new GridLayoutManager(this, 3));
        recyle.setAdapter(adapter);
        adapter.addAll(getData());
        SpaceDecoration itemDecoration = new SpaceDecoration(8);
        recyle.addItemDecoration(itemDecoration);
    }

    private List<String> getData() {
        List<String> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("Str" + i);
        }
        return list;
    }

    @OnClick(R.id.btn_close)
    public void onClick() {
        onStartClick(btnClose);
    }


    private class BannerAdapter extends RecyclerArrayAdapter<Object> {

        public BannerAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new TxtViewHolder(parent);
        }
    }


    public class TxtViewHolder extends BaseViewHolder<String> {
        TextView tv;

        public TxtViewHolder(ViewGroup parent) {
            super(parent, R.layout.adapter_str);
            tv = (TextView) itemView.findViewById(R.id.tv);
            list.add(itemView);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStartClick(v);
                }
            });
        }

        @Override
        public void setData(String data) {
            tv.setText(data);

        }
    }

    //    是否已经开始过动画
    boolean open = false;

    /**
     * 开始的动画
     *
     * @param view
     */
    public void onStartClick(View view) {
        final Rect viewRect = new Rect();
        view.getGlobalVisibleRect(viewRect);
        TransitionSet set = new TransitionSet()
                .addTransition(new Explode().setEpicenterCallback(new Transition.EpicenterCallback() {
                    @Override
                    public Rect onGetEpicenter(Transition transition) {
                        return viewRect;
                    }
                }).excludeTarget(view, true));
        TransitionManager.beginDelayedTransition(recyle, set);
        open = !open;
        if (open) {
            btnClose.setVisibility(View.VISIBLE);
            for (String str : getData()) {
                adapter.remove(str);
            }
        } else {
            btnClose.setVisibility(View.GONE);
            adapter.addAll(getData());
        }
    }


}
