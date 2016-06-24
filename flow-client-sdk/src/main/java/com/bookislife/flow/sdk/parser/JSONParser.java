package com.bookislife.flow.sdk.parser;

/**
 * Created by SidneyXu on 2016/06/14.
 */
public abstract class JSONParser {

    private static JSONParser parser;

    static {
        try {
            Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            parser = new JacksonParser();
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.google.gson.Gson");
                parser = new GsonParser();
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException("Jackson or Gson is required.");
            }
        }
    }

    public static void setParser(JSONParser parser) {
        if (JSONParser.parser == null) {
            JSONParser.parser = parser;
        }
    }

    public static <T> T decode(String json, Class<T> type) {
        return parser.internalDecode(json, type);
    }

    public static String encode(Object object) {
        return parser.internalEncode(object);
    }

    protected abstract <T> T internalDecode(String json, Class<T> type);

    protected abstract String internalEncode(Object object);
}
