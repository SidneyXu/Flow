package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/05/06.
 */
public class MySqlClientOptions {

    public final String url;
    public final String username;
    public final String password;

    MySqlClientOptions(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private String username;
        private String password;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder url(String host, int port) {
            if (port == 0) {
                port = 3306;
            }
            this.url = host + ":" + port;
            return this;
        }

        public Builder auth(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        public MySqlClientOptions create() {
            return new MySqlClientOptions(url, username, password);
        }
    }
}
