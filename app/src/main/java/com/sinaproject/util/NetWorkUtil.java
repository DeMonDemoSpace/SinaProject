package com.sinaproject.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * Created by DeMon on 2017/11/3.
 */

public class NetWorkUtil {
    /**
     * 判断网络状态
     */
    public static boolean NetWorkStatus(Context context) {
        //获取系统的连接服务
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 设置网络
     * Diaglog提醒
     *
     * @param context
     */
    public static void setNetWork(final Activity context) {
        AlertDialog.Builder b = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage("网络不可用，是否对网络进行设置？");
        b.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //跳转到设置网络界面
                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        }).setNeutralButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                context.finish();
            }
        }).create().show();
    }
}