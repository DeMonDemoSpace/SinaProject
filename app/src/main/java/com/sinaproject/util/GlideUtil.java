package com.sinaproject.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sinaproject.R;

/**
 * Created by DeMon on 2017/11/4.
 */

public class GlideUtil {
    /**
     * 根据图片url将图片显示在img控件上
     *
     * @param context 所属的Context
     * @param url     图片url
     * @param img     ImageView控件
     */
    public static void setImage(Context context, String url, ImageView img) {
        Glide.with(context).load(url).dontAnimate().fitCenter().placeholder(R.mipmap.icon).into(img);

    }
}
