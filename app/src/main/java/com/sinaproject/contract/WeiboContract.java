package com.sinaproject.contract;

import com.sinaproject.data.UserInfo;

import java.util.Map;

/**
 * Created by DeMon on 2017/11/5.
 */

public interface WeiboContract {
    interface View {
        void result(String s);
    }

    abstract class Presenter {
        public abstract void getWeibo(Map<String, Object> map);
    }
}
