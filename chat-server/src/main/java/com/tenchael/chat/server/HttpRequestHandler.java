package com.tenchael.chat.server;


import com.tenchael.chat.config.Configs;
import com.tenchael.chat.config.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final String wsUri = Configs.get(Constants.WEB_SOCKET_URI, "/ws");

    private final HttpRequestDispatcher dispatcher;

    public HttpRequestHandler(HttpRequestDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        ctx.channel().attr(HTTP_DISPATCHER).setIfAbsent(new HttpRequestDispatcher());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)
            throws Exception {
        if ("/".equals(request.uri())) {
            String uri = "/" + Configs.get(Constants.INDEX_PAGE, "index.html");
            request.setUri(uri);
        }
        LOGGER.info("request uri: {}, method: {}",
                request.uri(), request.method());


        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            dispatcher.dispatch(ctx, request);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("error occurs", cause);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("read complete");
        super.channelReadComplete(ctx);
        ctx.flush();
    }
}
