package com.bookislife.flow.server.domain;

import com.bookislife.flow.core.domain.BaseEntity;

/**
 * Created by SidneyXu on 2016/06/08.
 */
public class SessionToken extends BaseEntity {

    private String userId;
    private long expiredAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
