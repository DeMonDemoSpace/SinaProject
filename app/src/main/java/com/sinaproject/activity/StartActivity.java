package com.sinaproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.mvprr.base.BaseActivity;
import com.sinaproject.R;
import com.sinaproject.contract.OauthContract;
import com.sinaproject.contract.Presenter.OauthPresenter;
import com.sinaproject.data.Constant;
import com.sinaproject.data.SinaInfo;
import com.sinaproject.util.NetWorkUtil;
import com.sinaproject.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends BaseActivity<OauthPresenter> implements OauthContract.View {
    private static final String TAG = "StartActivity";
    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String token;

    @Override
    public void initPresenter() {
        mPresenter = new OauthPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void init() {
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
                token = (String) SPUtil.get(StartActivity.this, Constant.ACCESS_TOKEN, "");
                String uid = (String) SPUtil.get(StartActivity.this, Constant.UID, "");
                Log.i(TAG, "access_token:" + token + ",uid:" + uid);
                SinaInfo.getSinaInfo().setAccess_token(token);
                SinaInfo.getSinaInfo().setUid(uid);
                if (TextUtils.isEmpty(token)) {
                    btnLogin.setVisibility(View.VISIBLE);
                } else {
                    mPresenter.get_token_info(token);
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
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    @Override
    public void result(Object o) {
        Log.i(TAG, "result: " + o);
        try {
            JSONObject json = new JSONObject((String) o);
            String expire_in = json.getString(Constant.EXPIRE_IN);
            String uid = json.getString(Constant.UID);
            int time = Integer.parseInt(expire_in);
            if (time / 86400 < 1) {
                startActivity(new Intent(StartActivity.this, OauthActivity.class));
                finish();
            } else {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
