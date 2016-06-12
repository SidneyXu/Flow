package com.bookislife.flow.core.domain;

/**
 * Created by SidneyXu on 2016/06/04.
 */
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private boolean emailVerified;
    private String phone;
    private boolean phoneVeriifid;
    private String sessionToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneVeriifid() {
        return phoneVeriifid;
    }

    public void setPhoneVeriifid(boolean phoneVeriifid) {
        this.phoneVeriifid = phoneVeriifid;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
