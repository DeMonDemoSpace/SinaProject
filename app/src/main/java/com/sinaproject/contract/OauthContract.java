package com.sinaproject.contract;

import android.media.session.MediaSession;

import com.mvprr.base.BasePresenter;
import com.mvprr.base.BaseView;
import com.sinaproject.data.AccessToken;

import java.util.Map;

/**
 * Created by DeMon on 2017/11/4.
 */

public interface OauthContract {
    interface View extends BaseView {
        void result(Object o);
    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void getToken(Map<String, String> map);

        public abstract void get_token_info(String token);
    }
}
