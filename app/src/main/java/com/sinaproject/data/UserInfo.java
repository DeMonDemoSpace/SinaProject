package com.sinaproject.data;

/**
 * Created by DeMon on 2017/11/4.
 */

public class UserInfo {
    private String screen_name, description, avatar_hd;
    private int followers_count, friends_count, statuses_count;

    public String getScreen_name() {
        return screen_name;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public int getStatuses_count() {
        return statuses_count;
    }
}
