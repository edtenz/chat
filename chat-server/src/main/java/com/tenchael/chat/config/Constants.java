package com.tenchael.chat.config;

public interface Constants {
    String PORT = "port";
    String WEB_APP_BASE = "webAppBase";
    String PROPS_FILE = "app.config";
    String INDEX_PAGE = "indexPage";
    String WEB_SOCKET_URI = "webSocket.uri";


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
