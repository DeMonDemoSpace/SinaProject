package com.sinaproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DeMon on 2017/11/4.
 */

public class CommentFragment extends Fragment {

    public static CommentFragment instance = null;

    public static CommentFragment getInstance() {
        if (instance == null) {
            instance = new CommentFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setText("评论");
        return tv;
    }
}
