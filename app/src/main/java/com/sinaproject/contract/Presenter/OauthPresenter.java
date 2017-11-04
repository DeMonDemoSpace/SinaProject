package com.sinaproject.contract.Presenter;


import com.mvprr.progress.ObserverOnNextListener;
import com.sinaproject.api.Api;
import com.sinaproject.contract.OauthContract;

import java.util.Map;

/**
 * Created by DeMon on 2017/11/4.
 */

public class OauthPresenter extends OauthContract.Presenter {

    public OauthPresenter(OauthContract.View view) {
        super(view);
    }

    @Override
    public void getToken(Map<String, String> map) {
        mModel.Subscribe(mContext, Api.getApiService().getToken(map), new ObserverOnNextListener() {
            @Override
            public void onNext(Object o) {
                getView().result(o);
            }
        });
    }

    @Override
    public void get_token_info(String token) {
        mModel.Subscribe(mContext, Api.getApiService().getTokenInfo(token), new ObserverOnNextListener() {
            @Override
            public void onNext(Object o) {
                getView().result(o);
            }
        });
    }
}
