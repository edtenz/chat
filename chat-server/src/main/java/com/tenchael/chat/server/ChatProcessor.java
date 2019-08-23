package com.tenchael.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class ChatProcessor implements RequestProcessor {
    @Override
    public void submitRequest(ChannelHandlerContext ctx, FullHttpRequest request) {

    }
}
