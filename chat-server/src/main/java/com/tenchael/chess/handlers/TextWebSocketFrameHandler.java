package com.tenchael.chess.handlers;

import com.tenchael.chess.dto.ChatDto;
import com.tenchael.chess.dto.Header;
import com.tenchael.chess.dto.Operation;
import com.tenchael.chess.dto.Type;
import com.tenchael.chess.utils.BeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TextWebSocketFrameHandler
        extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            LOGGER.debug("HandshakeComplete event");
            ctx.pipeline().remove(HttpRequestHandler.class);

            Header header = new Header();
            header.setType(Type.resp);
            header.setOperation(Operation.connect);
            header.setClientId(ctx.channel().id().asShortText());

            ChatDto respDto = new ChatDto(header, new HashMap<>());
            String respText = BeanUtils.objectToJson(respDto);
            LOGGER.debug("handshake response: {}", respText);

            ctx.writeAndFlush(new TextWebSocketFrame(respText));
            group.add(ctx.channel());
        } else {
            LOGGER.debug("user event trigger: {}", evt);
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String text = msg.text();
        LOGGER.debug("read message: {}", text);
        ChatDto chatDto = BeanUtils.jsonToObject(text, ChatDto.class);
        ctx.fireChannelRead(chatDto);
    }


}
