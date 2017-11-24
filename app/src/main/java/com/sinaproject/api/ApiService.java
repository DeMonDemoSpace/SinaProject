package com.sinaproject.api;


import com.sinaproject.data.AccessToken;
import com.sinaproject.data.Constant;
import com.sinaproject.data.UserInfo;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    @GET("2/comments/timeline.json")
    Observable<String> getComment(@QueryMap Map<String, Object> map);

    @Multipart
    @POST("2/statuses/share.json")
    Observable<String> postWriteImage(@QueryMap Map<String, Object> map, @PartMap() Map<String, RequestBody> fileMap);

    @POST("2/statuses/share.json")
    Observable<String> postWrite(@QueryMap Map<String, Object> map);
}
