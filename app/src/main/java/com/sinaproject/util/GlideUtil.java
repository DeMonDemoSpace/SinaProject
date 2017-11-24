package com.sinaproject.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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


    /**
     * 根据uri获取文件真实路径
     * 由于不同手机尤其是小米，华为返回的uri格式不同，直接使用uri.getPath()会得到错误的路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore
                    .Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
