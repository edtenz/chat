package com.tenchael.chat.utils;

import com.tenchael.chat.config.Constants;

public class MixAll {

    public static byte[] stringToBytes(String str) {
        if (str == null) {
            return null;
        }
        return str.getBytes(Constants.ENCODING);
    }

    public static String bytesToString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, Constants.ENCODING);
    }

}
