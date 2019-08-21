package com.tenchael.chat;

import com.tenchael.chat.config.Configs;
import com.tenchael.chat.config.Constants;
import com.tenchael.chat.server.ChatHandler;
import com.tenchael.chat.server.HttpRequestDispatcher;
import com.tenchael.chat.server.HttpRequestHandler;
import com.tenchael.chat.server.TextWebSocketFrameHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


public class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;
    private final HttpRequestDispatcher dispatcher;

    public ChatServerInitializer(ChannelGroup group, HttpRequestDispatcher dispatcher) {
        this.group = group;
        this.dispatcher = dispatcher;
    }

    @Override
    protected void initChannel(Channel ch)
            throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler(dispatcher));
        pipeline.addLast(new WebSocketServerProtocolHandler(
                Configs.get(Constants.WEB_SOCKET_URI, "/ws")));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
        pipeline.addLast(new ChatHandler(group));
    }
}
