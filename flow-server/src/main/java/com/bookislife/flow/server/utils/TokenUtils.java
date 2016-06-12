package com.bookislife.flow.server.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by SidneyXu on 2016/06/08.
 */
public class TokenUtils {

    public static String generateToken(String identitiy) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] data = digest.digest(identitiy.getBytes(Charset.forName("UTF-8")));
            return null;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
