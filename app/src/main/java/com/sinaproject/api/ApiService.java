package com.sinaproject.api;


import com.sinaproject.data.AccessToken;
import com.sinaproject.data.Constant;
import com.sinaproject.data.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by DeMon on 2017/11/4.
 */

public interface ApiService {

    @POST("oauth2/access_token")
    Observable<AccessToken> getToken(@QueryMap Map<String, String> map);

    @POST("oauth2/get_token_info")
    Observable<String> getTokenInfo(@Query(Constant.ACCESS_TOKEN) String access_token);

    @GET("2/users/show.json")
    Observable<UserInfo> getUserInfo(@Query(Constant.ACCESS_TOKEN) String access_token, @Query(Constant.UID) String uid);

    @POST("oauth2/revokeoauth2")
    Observable<String> revokeToken(@Query(Constant.ACCESS_TOKEN) String access_token);

    @GET("2/statuses/home_timeline.json")
    Observable<String> getWeibo(@QueryMap Map<String, Object> map);
}
