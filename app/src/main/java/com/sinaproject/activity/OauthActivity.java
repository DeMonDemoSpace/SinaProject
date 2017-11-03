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
import com.sinaproject.data.SinaInfo;
import com.sinaproject.util.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OauthActivity extends BaseTopBarActivity {
    private static final String TAG = "OauthActivity";
    @BindView(R.id.webView)
    WebView webView;

    @Override
    public void initPresenter() {

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
                if (url.startsWith(SinaInfo.REDIRECT_URL)) {
                    String code = url.split("=")[1];
                    Log.i(TAG, "onPageStarted: " + code);

                    startActivity(new Intent(OauthActivity.this, MainActivity.class));
                }
            }

        };
        webView.setWebViewClient(client);
    }


}
