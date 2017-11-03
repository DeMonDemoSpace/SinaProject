package com.sinaproject.contract;

import com.mvprr.base.BasePresenter;
import com.mvprr.base.BaseView;

/**
 * Created by DeMon on 2017/11/4.
 */

public class OauthContract {
    public interface View extends BaseView {
        void result(String s);
    }

    public abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void getToken(String client_id, String client_secret, String grant_type, String code, String redirect_uri);

        ;
    }
}
