package com.bookislife.flow.sdk.parser;

/**
 * Created by SidneyXu on 2016/06/14.
 */
public abstract class JSONDecoder {

    protected abstract <T> T internalDecode(String json, Class<T> type);

    private static JSONDecoder decoder;

    static {
        try {
            Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            decoder = new JacksonDecoder();
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.google.gson.Gson");
                decoder = new GsonDecoder();
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException("Jackson or Gson is required.");
            }
        }
    }

    public static void setDecoder(JSONDecoder decoder) {
        if (JSONDecoder.decoder == null) {
            JSONDecoder.decoder = decoder;
        }
    }

    public static <T> T decode(String json, Class<T> type) {
        return decoder.internalDecode(json, type);
    }

}
