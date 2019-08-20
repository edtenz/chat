package com.tenchael.chess.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.tenchael.chess.config.Constants.ACTION_PATTERN;
import static com.tenchael.chess.config.Constants.RESOURCES_PATTERN;

public class HttpRequestDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestDispatcher.class);

    public void dispatch(ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {
        String uri = request.uri();
        if (isResouce(uri)) {
            ResourceProcessor.process(ctx, request);
            return;
        } else if (isAction(uri)) {
            //TODO action processor
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
