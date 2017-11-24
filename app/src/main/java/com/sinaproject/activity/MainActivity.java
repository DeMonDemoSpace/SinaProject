package com.sinaproject.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sinaproject.R;
import com.sinaproject.data.SinaInfo;
import com.sinaproject.fragment.CommentFragment;
import com.sinaproject.fragment.MineFragment;
import com.sinaproject.fragment.WeiboFragment;
import com.sinaproject.fragment.WriteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.valuesfeng.picker.utils.PicturePickerUtils;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener, WbShareCallback {
    private static final String TAG = "MainActivity";
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    public static List<Uri> mSelected = new ArrayList<>();
    public static WbShareHandler shareHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WbSdk.install(this, new AuthInfo(this, SinaInfo.APP_KEY, SinaInfo.REDIRECT_URL, SinaInfo.SCOPE));
        initView();
    }

    private void initView() {
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        shareHandler.setProgressColor(R.color.colorPrimary);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        fragmentList.add(WeiboFragment.getInstance());
        fragmentList.add(CommentFragment.getInstance());
        fragmentList.add(WriteFragment.getInstance());
        fragmentList.add(MineFragment.getInstance());
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        viewPager.setAdapter(mFragmentPagerAdapter);
        viewPager.setCurrentItem(0);//设置起始位置
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.setSelectedItemId(R.id.nav_weibo);
                        break;
                    case 1:
                        bottomNavigation.setSelectedItemId(R.id.nav_comment);
                        break;
                    case 2:
                        bottomNavigation.setSelectedItemId(R.id.nav_write);
                        break;
                    case 3:
                        bottomNavigation.setSelectedItemId(R.id.nav_mine);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_weibo:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.nav_comment:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.nav_write:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.nav_mine:
                viewPager.setCurrentItem(3);
                return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            mSelected = new ArrayList<>();
            mSelected = PicturePickerUtils.obtainResult(data);
            WriteFragment.handler.sendEmptyMessage(0x001);
        }
    }

    @Override
    public void onWbShareSuccess() {
        bottomNavigation.setSelectedItemId(R.id.nav_weibo);
        viewPager.setCurrentItem(0);
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
        WriteFragment.handler.sendEmptyMessage(0x002);
    }

    @Override
    public void onWbShareFail() {
        Toast.makeText(this, getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onWbShareCancel() {
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            final AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
            alterDialog.setMessage("确定退出应用？");
            alterDialog.setCancelable(true);
            alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            alterDialog.setNegativeButton("取消", null);
            alterDialog.show();
        }
        return false;
    }
}
