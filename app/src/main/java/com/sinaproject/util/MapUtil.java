package com.sinaproject.util;

import com.sinaproject.data.Constant;
import com.sinaproject.data.SinaInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeMon on 2017/11/4.
 */

public class MapUtil {
    /**
     * 获取包含access_token的Map
     *
     * @return
     */
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.ACCESS_TOKEN, SinaInfo.getSinaInfo().getAccess_token());
        return map;
    }
}
