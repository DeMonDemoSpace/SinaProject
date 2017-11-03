package com.sinaproject.api;


import com.sinaproject.data.AccessToken;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DeMon on 2017/11/4.
 */

public interface ApiService {

    @POST("oauth2/access_token")
    Observable<AccessToken> getToken(@Query("client_id") String client_id, @Query("client_secret") String client_secret,
                                     @Query("grant_type") String grant_type, @Query("code") String code, @Query("redirect_uri") String redirect_uri);
}
