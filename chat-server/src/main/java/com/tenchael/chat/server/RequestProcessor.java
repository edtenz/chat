package com.tenchael.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface RequestProcessor {

    void submitRequest(ChannelHandlerContext ctx, FullHttpRequest request);

}
