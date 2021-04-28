package com.tenchael.chat.server;

import com.tenchael.chat.dto.Header;
import com.tenchael.chat.dto.RespDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatHandler extends SimpleChannelInboundHandler<RespDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private final ChannelGroup group;

    public ChatHandler(ChannelGroup group) {
        this.group = group;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("channel active");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RespDto msg)
            throws Exception {
        Header reqHeader = msg.getHeader();
        LOGGER.debug("read content: {}", reqHeader);
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("channelUnregistered: {}", ctx.channel().id().asShortText());
        String channelId = ctx.channel().id().asShortText();
        super.channelUnregistered(ctx);
    }


}
