package com.tenchael.chat.server;

import com.tenchael.chat.utils.MixAll;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

abstract public class AbstractProcessor implements RequestProcessor {

    protected void writeResponse(ChannelHandlerContext ctx, HttpRequest request, String content) {
        ByteBuf respBuf = Unpooled.wrappedBuffer(MixAll.stringToBytes(content));
        writeResponse(ctx, request, respBuf);
    }


    protected void writeResponse(ChannelHandlerContext ctx, HttpRequest request, ByteBuf buf) {
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse response = new DefaultFullHttpResponse(
                request.protocolVersion(),
                HttpResponseStatus.OK, buf,
                false);
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        headers.set(HttpHeaderNames.CONNECTION, HttpUtil.isKeepAlive(request));
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.write(response, ctx.voidPromise());
        }
    }

}
