package com.bookislife.flow.server.domain;

import com.bookislife.flow.server.utils.DigestUtils;

import java.util.UUID;

/**
 * Created by SidneyXu on 2016/06/26.
 */
public class AppKey {

    private String key;

    public AppKey() {
        String uuid = UUID.randomUUID().toString();
        key = DigestUtils.base64(DigestUtils.md5(uuid.replace("/", "").getBytes()));
    }
}
