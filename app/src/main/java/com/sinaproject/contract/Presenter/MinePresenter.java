package com.sinaproject.contract.Presenter;

import android.content.Context;

import com.mvprr.base.BaseModel;
import com.mvprr.progress.ObserverOnNextListener;
import com.sinaproject.api.Api;
import com.sinaproject.contract.MineContract;
import com.sinaproject.data.UserInfo;

/**
 * Created by DeMon on 2017/11/4.
 */

public class MinePresenter extends MineContract.Presenter {
    private BaseModel<UserInfo> model = new BaseModel<>();
    private MineContract.View view;
    private Context context;

    public MinePresenter(Context context, MineContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getUserInfo(String token, String uid) {
        model.Subscribe(context, Api.getApiService().getUserInfo(token, uid), new ObserverOnNextListener() {
            @Override
            public void onNext(Object o) {
                view.result((UserInfo) o);
            }
        });
    }
}
