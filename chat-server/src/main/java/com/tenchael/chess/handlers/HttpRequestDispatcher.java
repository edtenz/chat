package com.tenchael.chess.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.tenchael.chess.config.Constants.RESOURCES_PATTERN;

public class HttpRequestDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestDispatcher.class);

    public void dispatch(ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {
        String uri = request.uri();
        if (isResouce(uri)) {
            ResourceProcessor.process(ctx, request);
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


}
