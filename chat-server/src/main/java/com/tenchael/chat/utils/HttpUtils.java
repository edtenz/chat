package com.tenchael.chat.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    /**
     * extract params map from uri
     *
     * @param uriString
     * @return
     */
    public static Map<String, String> parameterMap(String uriString) {
        URI uri = URI.create(uriString);
        String query = uri.getQuery();
        HashMap<String, String> params = new HashMap<>();
        if (query == null || query.trim().length() == 0) {
            return params;
        }
        String[] items = query.split("&");
        if (items != null) {
            for (String item : items) {
                String[] kv = item.split("=");
                if (kv.length == 1) {
                    params.put(kv[0], "");
                } else {
                    params.put(kv[0], kv[1]);
                }
            }
        }
        return params;
    }

    public static String uriPath(String uriString) {
        URI uri = URI.create(uriString);
        return uri.getPath();
    }

}
