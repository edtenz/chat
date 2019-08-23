package com.tenchael.chat.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface Constants {
    String PORT = "port";
    String WEB_APP_BASE = "webAppBase";
    String PROPS_FILE = "app.config";
    String INDEX_PAGE = "indexPage";
    String WEB_SOCKET_URI = "webSocket.uri";

    Charset ENCODING = StandardCharsets.UTF_8;


    String[] RESOURCES_PATTERN = new String[]{
            ".html",
            ".css",
            ".js",
            ".ico"
    };

    String[] ACTION_PATTERN = new String[]{
            ".action",
            ".do"
    };

}
