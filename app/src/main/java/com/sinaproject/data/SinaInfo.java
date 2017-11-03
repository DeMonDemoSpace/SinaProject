package com.sinaproject.data;

/**
 * Created by DeMon on 2017/11/4.
 */

public class SinaInfo {
    /**
     * 当前 DEMO 应用的开发者ID
     */
    public static final String CLIENT_ID = "2617018442";
    /**
     * 开发者网站的回调地址
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    /**
     * 应用密匙
     */
    public static final String APP_SECRET = "3da286b566e32b146a806d63889f020d";
    /**
     * 授权页面引用地址，根据上面参数生成
     * https://api.weibo.com/oauth2/authorize?client_id=2887521880&redirect_uri=https://api.weibo.com/oauth2/default.html&response_type=code
     */
    public static final String URL = "https://api.weibo.com/oauth2/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=code";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
