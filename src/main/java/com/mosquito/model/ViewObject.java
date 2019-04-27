package com.mosquito.model;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewObject：对数据进行封装
 * 方便传递任何数据到velocity
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
