package com.tenchael.chat.server;

import com.tenchael.chat.config.Configs;
import com.tenchael.chat.config.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.tenchael.chat.config.Constants.ACTION_PATTERN;
import static com.tenchael.chat.config.Constants.RESOURCES_PATTERN;

public class HttpRequestDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestDispatcher.class);

    private final RequestProcessor processor = new ResourceProcessor(Configs.get(Constants.WEB_APP_BASE,
            Configs.DEFAULT_WEB_APP_BASE));


    private final Map<String, RequestProcessor> actionProcessors = new HashMap<>();

    public HttpRequestDispatcher() {
        actionProcessors.put("/auth.action", new AuthProcessor());
    }

    public void dispatch(ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {
        String uri = request.uri();
        if (isResouce(uri)) {
            LOGGER.trace("request is for resource {}", uri);
            processor.submitRequest(ctx, request);
            return;
        } else if (isAction(uri)) {
            //TODO action processor
            LOGGER.trace("request is a action {}", uri);
            actionProcessors.get(uri).submitRequest(ctx, request);
            return;
        }
    }

    private boolean isResouce(String uri) {
        int paramStart = uri.indexOf("?");
        if (paramStart != -1) {
            uri = uri.substring(0, paramStart);
        }
        for (String resourcePattern : RESOURCES_PATTERN) {
            if (uri.endsWith(resourcePattern)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAction(String uri) {
        int paramStart = uri.indexOf("?");
        if (paramStart != -1) {
            uri = uri.substring(0, paramStart);
        }
        for (String actionPattern : ACTION_PATTERN) {
            if (uri.endsWith(actionPattern)) {
                return true;
            }
        }
        return false;
    }


}
