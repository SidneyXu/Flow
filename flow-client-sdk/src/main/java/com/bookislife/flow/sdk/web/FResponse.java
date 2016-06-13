package com.bookislife.flow.sdk.web;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class FResponse {

    private final FHeader header;
    private final int statusCode;
    private final InputStream inputStream;

    public FResponse(FHeader header, int statusCode, InputStream inputStream) {
        this.header = header;
        this.statusCode = statusCode;
        this.inputStream = inputStream;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public FHeader getHeader() {
        return header;
    }

    public String getContentType() {
        return header.getContentType();
    }

    public boolean isJsonResponse() {
        return getContentType() != null && getContentType().contains("application/json");
    }

    public boolean isSuccessful() {
        return (statusCode >= 200 && statusCode < 300) || statusCode == 304;
    }

    public byte[] getBodyAsByte() {
        if (inputStream == null) return null;
        ByteOutputStream outputStream = new ByteOutputStream();
        byte[] buffer = new byte[4098];
        int len = -1;
        try {
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getBodyAsString() {
        byte[] bytes = getBodyAsByte();
        if (bytes == null || bytes.length == 0) return null;
        return new String(bytes, Charset.forName("UTF-8"));
    }

    public InputStream getBodyAsStream() {
        return inputStream;
    }

    public void writeTo(OutputStream outputStream) {
        writeTo(outputStream, true);
    }

    public void writeTo(OutputStream outputStream, boolean autoClose) {
        if (inputStream == null || outputStream == null) return;
        byte[] buffer = new byte[4098];
        int len = -1;
        try {
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (autoClose) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private FHeader header;
        private int httpCode;
        private InputStream inputStream;

        public Builder addHeader(String key, String value) {
            if (header == null) {
                header = new FHeader();
            }
            header.put(key, value);
            return this;
        }

        public Builder httpCode(int code) {
            this.httpCode = code;
            return this;
        }

        public Builder data(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public FResponse build() {
            return new FResponse(header, httpCode, inputStream);
        }
    }
}
