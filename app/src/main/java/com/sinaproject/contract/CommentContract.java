package com.sinaproject.contract;

import java.util.Map;

/**
 * Created by DeMon on 2017/11/5.
 */

public interface CommentContract {
    interface View {
        void result(String s);
    }

    abstract class Presenter {
        public abstract void getComment(Map<String, Object> map);
    }
}
