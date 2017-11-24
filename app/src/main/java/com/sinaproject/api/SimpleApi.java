package com.sinaproject.api;

import com.mvprr.base.BaseApi;

/**
 * Created by DeMon on 2017/11/4.
 */

public class SimpleApi {
    private String baseUrl = "https://api.weibo.com/";

    private volatile static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (SimpleApi.class) {
                if (apiService == null) {
                    new SimpleApi();
                }
            }
        }
        return apiService;
    }

    private SimpleApi() {
        BaseApi baseApi = new BaseApi();
        apiService = baseApi.getSimpleRetrofit(baseUrl).create(ApiService.class);
    }

}
