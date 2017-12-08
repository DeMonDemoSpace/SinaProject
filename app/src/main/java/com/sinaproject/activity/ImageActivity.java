package com.sinaproject.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.sinaproject.R;
import com.sinaproject.adapter.ImgAdapter;
import com.sinaproject.data.Constant;

import java.util.ArrayList;

/**
 * Created by DeMon on 2017/12/9.
 */


public class ImageActivity extends Activity {
    private ViewPager pager;
    private TextView num;
    ArrayList<String> imgs;
    int pos;
    private ImgAdapter imgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_papager);
        pager = (ViewPager) findViewById(R.id.pager);
        num = (TextView) findViewById(R.id.num);

        imgs = getIntent().getStringArrayListExtra(Constant.EXTRA_IMAGE_URLS);
        pos = getIntent().getIntExtra(Constant.EXTRA_IMAGE_INDEX, 0);
        num.setText((pos + 1) + "/" + imgs.size());
        imgAdapter = new ImgAdapter(this, imgs);
        pager.setAdapter(imgAdapter);

        pager.setCurrentItem(pos);//设置起始位置


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                num.setText((position + 1) + "/" + imgs.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
