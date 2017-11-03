package com.sinaproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.sinaproject.R;
import com.sinaproject.data.Constant;
import com.sinaproject.util.NetWorkUtil;
import com.sinaproject.util.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends Activity {

    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(2000);
        imgStart.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!NetWorkUtil.NetWorkStatus(StartActivity.this)) {
                    NetWorkUtil.setNetWork(StartActivity.this);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (SPUtil.get(StartActivity.this, Constant.TOKEN, null) == null) {
                    btnLogin.setVisibility(View.VISIBLE);
                } else {
                    finish();
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        startActivity(new Intent(StartActivity.this, OauthActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }
}
