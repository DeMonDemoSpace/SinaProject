package com.sinaproject.contract;

import com.mvprr.base.BasePresenter;
import com.mvprr.base.BaseView;
import com.sinaproject.data.UserInfo;

import java.util.Map;

/**
 * Created by DeMon on 2017/11/4.
 */

public interface MineContract {
    interface View {
        void result(UserInfo info);
    }

    abstract class Presenter {
        public abstract void getUserInfo(String token, String uid);
    }
}
