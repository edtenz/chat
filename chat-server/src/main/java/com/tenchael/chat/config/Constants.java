package com.tenchael.chat.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    public static String PORT = "port";
    public static String WEB_APP_BASE = "webAppBase";
    public static String PROPS_FILE = "app.config";
    public static String INDEX_PAGE = "indexPage";
    public static String WEB_SOCKET_URI = "webSocket.uri";

    public static Charset ENCODING = StandardCharsets.UTF_8;


    public static String[] RESOURCES_PATTERN = new String[]{
            ".html",
            ".css",
            ".js",
            ".ico"
    };

    public static String[] ACTION_PATTERN = new String[]{
            ".action",
            ".do"
    };

}
