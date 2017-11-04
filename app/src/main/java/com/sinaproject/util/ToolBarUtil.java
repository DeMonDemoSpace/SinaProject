package com.sinaproject.util;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sinaproject.R;

/**
 * Created by DeMon on 2017/11/4.
 */

public class ToolBarUtil {
    /**
     * 置标题及按钮功能
     *
     * @param activity
     * @param title
     */
    public static void setToolBar(final AppCompatActivity activity, String title) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

}
