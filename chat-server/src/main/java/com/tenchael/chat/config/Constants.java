package com.tenchael.chat.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    public static final String PORT = "port";
    public static final String WEB_APP_BASE = "webAppBase";
    public static final String PROPS_FILE = "app.config";
    public static final String INDEX_PAGE = "indexPage";
    public static final String WEB_SOCKET_URI = "webSocket.uri";

    public static final Charset ENCODING = StandardCharsets.UTF_8;


    public static final String[] RESOURCES_PATTERN = new String[]{
            ".html",
            ".css",
            ".js",
            ".ico"
    };

    public static final String[] ACTION_PATTERN = new String[]{
            ".action",
            ".do"
    };

    private Constants() {
        throw new IllegalStateException("can not access private constructor!");
    }

}
