package com.sinaproject.data;

/**
 * Created by DeMon on 2017/11/22.
 */

public class Comments {
    private String created_at, text, source;
    private UserInfo user;
    private WeiBo_Status status;

    public String getCreated_at() {
        return created_at;
    }

    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }

    public UserInfo getUser() {
        return user;
    }

    public WeiBo_Status getStatus() {
        return status;
    }
}
