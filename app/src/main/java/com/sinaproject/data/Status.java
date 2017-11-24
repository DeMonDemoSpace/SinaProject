package com.sinaproject.data;

import java.util.List;

/**
 * Created by DeMon on 2017/11/24.
 */

public class Status {
    public List<Pic_urls> pic_urls;
    private String created_at, text, source;
    private long id;
    private int reposts_count, comments_count, attitudes_count;
    private UserInfo user;

    public List<Pic_urls> getPic_urls() {
        return pic_urls;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }

    public long getId() {
        return id;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public UserInfo getUser() {
        return user;
    }
}
