package com.bookislife.flow.data;

import com.google.common.base.Joiner;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by SidneyXu on 2016/05/06.
 */
public class MongoClientOptions {

    private List<String> urls;

    MongoClientOptions(List<String> urls) {
        this.urls = urls;
    }

    public String getConnectionUrl() {
        return Joiner.on(',').join(urls);
    }

    public List<ServerAddress> getServerAddress() {
        return urls.stream()
                .map(s -> s.split(":"))
                .map(arr -> new ServerAddress(arr[0], Integer.parseInt(arr[1])))
                .collect(Collectors.toList());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> urls = new ArrayList<>();

        public Builder urls(List<String> urls) {
            this.urls = urls;
            return this;
        }

        public Builder url(String url) {
            if (url.contains(":")) {
                urls.add(url);
            } else {
                url(url, 0);
            }
            return this;
        }

        public Builder url(String host, int port) {
            if (port == 0) {
                port = 27017;
            }
            urls.add(host + ":" + port);
            return this;
        }

        public MongoClientOptions create() {
            return new MongoClientOptions(urls);
        }
    }
}
