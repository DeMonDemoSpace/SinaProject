package com.sinaproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mvprr.base.BaseTopBarActivity;
import com.sinaproject.R;
import com.sinaproject.contract.OauthContract;
import com.sinaproject.contract.Presenter.OauthPresenter;
import com.sinaproject.data.AccessToken;
import com.sinaproject.data.Constant;
import com.sinaproject.data.SinaInfo;
import com.sinaproject.util.DialogUtil;
import com.sinaproject.util.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OauthActivity extends BaseTopBarActivity<OauthPresenter> implements OauthContract.View {
    private static final String TAG = "OauthActivity";
    @BindView(R.id.webView)
    WebView webView;

    @Override
    public void initPresenter() {
        mPresenter = new OauthPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth;
    }

    @Override
    public void init() {
        setTopBarText("微博授权登陆");


        //管理WebView
        WebSettings webSettings = webView.getSettings();
        //启用JavaScript调用功能
        webSettings.setJavaScriptEnabled(true);
        //启用缩放网页功能
        webSettings.setSupportZoom(true);
        //获取焦点
        webView.requestFocus();
        webView.loadUrl(SinaInfo.URL);

        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(TAG, "onPageStarted: " + url);
                if (url.equals(SinaInfo.CANCEL_URL)) {
                    finish();
                } else if (url.startsWith(SinaInfo.REDIRECT_URL)) {
                    String code = url.split("=")[1];
                    Log.i(TAG, "onPageStarted: " + code);
                    Map<String, String> map = new HashMap<>();
                    map.put("client_id", SinaInfo.CLIENT_ID);
                    map.put("client_secret", SinaInfo.APP_SECRET);
                    map.put("grant_type", "authorization_code");
                    map.put("code", code);
                    map.put("redirect_uri", SinaInfo.REDIRECT_URL);
                    mPresenter.getToken(map);
                }

            }

        };
        webView.setWebViewClient(client);
    }


    @Override
    public void result(Object o) {
        AccessToken token = (AccessToken) o;
        if (token != null) {
            SPUtil.put(this, Constant.ACCESS_TOKEN, token.getAccess_token());
            SPUtil.put(this, Constant.UID, token.getUid());
            SinaInfo.getSinaInfo().setAccess_token(token.getAccess_token());
            SinaInfo.getSinaInfo().setUid(token.getUid());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
