package com.sinaproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeMon on 2017/11/6.
 */

public class WeiBo_Status {
    public List<Pic_urls> pic_urls;
    private String created_at, text, source;
    private long id;
    private int reposts_count, comments_count, attitudes_count;
    private User user;
    private WeiBo_Status retweeted_status;

    public int getReposts_count() {
        return reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public WeiBo_Status getRetweeted_status() {
        return retweeted_status;
    }

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

    public User getUser() {
        return user;
    }

    public static class Pic_urls {
        public String thumbnail_pic;

        public String getThumbnail_pic() {
            return thumbnail_pic;
        }
    }

    public static class User {
        @SerializedName("id")
        public long id;
        @SerializedName("screen_name")
        public String screen_name;
        @SerializedName("avatar_hd")
        public String avatar_hd;

        public long getId() {
            return id;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public String getAvatar_hd() {
            return avatar_hd;
        }
    }
}
