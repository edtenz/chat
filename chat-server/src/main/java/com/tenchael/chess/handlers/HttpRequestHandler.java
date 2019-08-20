package com.tenchael.chess.handlers;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final String wsUri;

    private static final AttributeKey<HttpRequestDispatcher> HTTP_DISPATCHER =
            AttributeKey.valueOf("HttpRequestDispatcher");

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().attr(HTTP_DISPATCHER).setIfAbsent(new HttpRequestDispatcher());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)
            throws Exception {
        LOGGER.debug("request uri: {}", request.uri());
        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            ctx.channel().attr(HTTP_DISPATCHER).get()
                    .dispatch(ctx, request);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}
