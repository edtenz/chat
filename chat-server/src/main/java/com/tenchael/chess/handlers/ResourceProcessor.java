package com.tenchael.chess.handlers;

import com.tenchael.chess.config.Configs;
import com.tenchael.chess.config.Constants;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ResourceProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceProcessor.class);

    private static final String WEB_BASE = Configs.get(Constants.WEB_APP_BASE,
            Configs.DEFAULT_WEB_APP_BASE);

    public static void process(ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {
        if (HttpUtil.is100ContinueExpected(request)) {
            send100Continue(ctx);
        }

        String path = resourcePath(request);
        LOGGER.trace("read file: {}", path);
        RandomAccessFile file = new RandomAccessFile(path, "r");
        HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
        contentTypeSetting(request, response);

        boolean keepAlive = HttpUtil.isKeepAlive(request);

        if (keepAlive) {
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write(response);

        if (ctx.pipeline().get(SslHandler.class) == null) {
            ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
        } else {
            ctx.write(new ChunkedNioFile(file.getChannel()));
        }
        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }


    private static void contentTypeSetting(HttpRequest request, HttpResponse response) {
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

        if (request.uri().endsWith(".js")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/x-javascript");
        }
        if (request.uri().endsWith(".css")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/css");
        }
        if (request.uri().endsWith(".ico")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "image/x-icon");
        }
    }

    private static String resourcePath(HttpRequest request) {
        String path;
        if ("/".equals(request.uri())) {
            path = WEB_BASE + Configs.get(Constants.INDEX_PAGE, "index.html");
        } else {
            int index = request.uri().indexOf("?");
            if (index == -1) {
                path = WEB_BASE + request.uri();
            } else {
                path = WEB_BASE + request.uri().substring(0, index);
            }
        }
        return path;
    }
}
