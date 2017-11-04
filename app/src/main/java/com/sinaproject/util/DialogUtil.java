package com.sinaproject.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by DeMon on 2017/11/4.
 */

public class DialogUtil {
    /**
     * 数据加载进度框
     *
     * @param context
     * @return
     */
    public static ProgressDialog getProgressDialog(final Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        //dialog.setTitle("温馨提示");
        dialog.setMessage("加载中...");
        dialog.setCancelable(true);

        return dialog;
    }
}
