package com.sinaproject.contract.Presenter;

import android.content.Context;

import com.mvprr.base.BaseModel;
import com.mvprr.progress.ObserverOnNextListener;
import com.sinaproject.api.Api;
import com.sinaproject.contract.CommentContract;
import com.sinaproject.contract.WeiboContract;
import com.sinaproject.data.Constant;
import com.sinaproject.data.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by DeMon on 2017/11/5.
 */

public class CommentPresenter extends CommentContract.Presenter {
    private BaseModel<UserInfo> model = new BaseModel<>();
    private CommentContract.View view;
    private Context context;

    public CommentPresenter(Context context, CommentContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getComment(Map<String, Object> map) {
        model.Subscribe(context, Api.getApiService().getComment(map), new ObserverOnNextListener() {
            @Override
            public void onNext(Object o) {
                try {
                    JSONObject object = new JSONObject((String) o);
                    JSONArray array = object.getJSONArray(Constant.COMMENTS);
                    view.result(array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
