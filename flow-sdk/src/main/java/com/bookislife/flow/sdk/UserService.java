package com.bookislife.flow.sdk;

import com.bookislife.flow.core.domain.User;

/**
 * Created by SidneyXu on 2016/06/12.
 */
public interface UserService {

    void register(User user);

    void login(String username, String password);
}
