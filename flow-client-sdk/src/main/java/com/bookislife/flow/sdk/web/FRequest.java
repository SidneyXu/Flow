package com.bookislife.flow.sdk.web;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class FRequest {

    private final FHeader header;
    private final String url;
    private final String method;
    private final FBody body;

    private FRequest(FHeader header, String url, String method, FBody body) {
        this.header = header;
        this.url = url;
        this.method = method;
        this.body = body;
    }

    String getUrl() {
        return url;
    }

    FHeader getHeader() {
        return header;
    }

    String getMethod() {
        return method;
    }

    FBody getBody() {
        return body;
    }

    public boolean hasHeader() {
        return header != null && header.size() > 0;
    }

    public boolean shouldHasBody() {
        return !method.equals("GET");
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private FHeader header;
        private String url;
        private String method;
        private FBody body;

        public Builder() {
        }

        public Builder(String url) {
            config(url, "GET");
        }

        private Builder config(String url, String method) {
            this.url = url;
            this.method = method;
            return this;
        }

        public Builder post(String url) {
            return config(url, "POST");
        }

        public Builder put(String url) {
            return config(url, "PUT");
        }

        public Builder delete(String url) {
            return config(url, "DELETE");
        }

        public Builder get(String url) {
            return config(url, "GET");
        }

        public Builder headers(FHeader header) {
            this.header = header;
            return this;
        }

        public Builder addHeader(String key, String value) {
            if (header == null) {
                header = new FHeader();
            }
            header.put(key, value);
            return this;
        }

        public Builder body(FBody body) {
            this.body = body;
            return this;
        }

        public FRequest build() {
            return new FRequest(header, url, method, body);
        }
    }
}
