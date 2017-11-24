package com.sinaproject.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.sinaproject.R;
import com.sinaproject.data.Constant;

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

    /**
     * 提示dialog
     * positiveButton的回调
     *
     * @param context
     * @param dialogView
     */
    public static void doDialog(final Context context, String msg, final DoDialogView dialogView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(Constant.PS).setMessage(msg);
        builder.setNegativeButton(R.string.negative, null);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogView != null) {
                    dialogView.yes();
                }
            }
        });
        builder.create().show();
    }
}
