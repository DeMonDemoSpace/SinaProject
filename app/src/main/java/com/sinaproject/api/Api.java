package com.sinaproject.api;

import com.mvprr.base.BaseApi;

/**
 * Created by DeMon on 2017/11/4.
 */

public class Api {
    private String baseUrl = "https://api.weibo.com/";//阿里云根据地区名获取经纬度接口

    private volatile static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    private Api() {
        BaseApi baseApi = new BaseApi();
        apiService = baseApi.getRetrofit(baseUrl).create(ApiService.class);
    }
}
