package com.sinaproject.activity;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sinaproject.R;
import com.sinaproject.fragment.CommentFragment;
import com.sinaproject.fragment.MineFragment;
import com.sinaproject.fragment.WeiboFragment;
import com.sinaproject.fragment.WriteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
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


}
