package com.tenchael.chat.server;

import com.tenchael.chat.config.Configs;
import com.tenchael.chat.config.Constants;
import com.tenchael.chat.exceptions.ProcessException;
import com.tenchael.chat.utils.HttpUtils;
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

public class ResourceProcessor implements RequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceProcessor.class);

    private final String resourceBase;

    public ResourceProcessor(String resourceBase) {
        this.resourceBase = resourceBase;
    }

    @Override
    public void submitRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (HttpUtil.is100ContinueExpected(request)) {
            send100Continue(ctx);
        }

        String path = resourcePath(request);
        LOGGER.trace("read file: {}", path);
        try (RandomAccessFile file = new RandomAccessFile(path, "r")) {
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
        } catch (IOException e) {
            LOGGER.error("request process error", e);
            throw new ProcessException(e);
        }

    }

    private void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }


    private void contentTypeSetting(HttpRequest request, HttpResponse response) {
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

        String uri = HttpUtils.uriPath(request.uri());
        if (uri.endsWith(".js")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/x-javascript");
        }
        if (uri.endsWith(".css")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/css");
        }
        if (uri.endsWith(".ico")) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "image/x-icon");
        }
    }

    private String resourcePath(HttpRequest request) {
        String path;
        if ("/".equals(request.uri())) {
            path = resourceBase + Configs.get(Constants.INDEX_PAGE, "index.html");
        } else {
            int index = request.uri().indexOf('?');
            if (index == -1) {
                path = resourceBase + request.uri();
            } else {
                path = resourceBase + request.uri().substring(0, index);
            }
        }
        return path;
    }
}
