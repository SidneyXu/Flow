package com.bookislife.flow.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/06/24.
 */
public class BaseResponse<K, V> extends HashMap<K, V> {

    public String getString(String key) {
        return "" + get(key);
    }

    public Integer getInt(String key) {
        return Integer.valueOf(getString(key));
    }

    public Long getLong(String key) {
        return Long.valueOf(getString(key));
    }

    public Integer getErrorCode() {
        return getInt("error_code");
    }

    public String getErrorMessage() {
        return getString("error_message");
    }

    public Long getCreatedAt() {
        return getLong("createdAt");
    }

    public String getId() {
        return getString("id");
    }

    public Integer getCount() {
        return getInt("count");
    }

    public Map<String, Object> getData() {
        return (Map<String, Object>) get("data");
    }

    public List<Map<String, Object>> getDatas() {
        return (List<Map<String, Object>>) get("datas");
    }
}
