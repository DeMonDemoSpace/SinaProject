package com.sinaproject.contract.Presenter;

import com.sinaproject.contract.OauthContract;

/**
 * Created by DeMon on 2017/11/4.
 */

public class OauthPresenter extends OauthContract.Presenter {

    public OauthPresenter(OauthContract.View view) {
        super(view);
    }

    @Override
    public void getToken(String client_id, String client_secret, String grant_type, String code, String redirect_uri) {

    }
}
