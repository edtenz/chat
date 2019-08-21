package com.tenchael.chat.server;

import com.tenchael.chat.dto.AuthDto;
import com.tenchael.chat.dto.RespDto;
import com.tenchael.chat.dto.Status;
import com.tenchael.chat.utils.BeanUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthProcessor implements RequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthProcessor.class);

    private final Map<String, String> userMap = new HashMap<>();


    public AuthProcessor() {
        userMap.put("test", "123456");
        userMap.put("root", "root");
        userMap.put("teng", "teng");
    }

    @Override
    public void submitRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        String content = request.content().toString(StandardCharsets.UTF_8);
        AuthDto auth = BeanUtils.jsonToObject(content, AuthDto.class);
        String upass = userMap.get(auth.getUsername());

        RespDto respDto = new RespDto();
        if (upass == null) {
            //user not exists
            LOGGER.info("user {} does not exists", auth.getUsername());
            respDto.setStatus(Status.notOk);
            respDto.setErrMessage(String.format("user [%s] not exists", auth.getUsername()));
            byte[] json = BeanUtils.objectToJson(respDto).getBytes(StandardCharsets.UTF_8);
            writeResponse(ctx, request, Unpooled.wrappedBuffer(json), String.valueOf(json.length));
            return;
        }
    }


    private void writeResponse(ChannelHandlerContext ctx, HttpRequest request, ByteBuf buf, CharSequence contentLength) {
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse response = new DefaultFullHttpResponse(
                request.protocolVersion(),
                HttpResponseStatus.OK, buf,
                false);
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        headers.set(HttpHeaderNames.CONNECTION, HttpUtil.isKeepAlive(request));
        headers.set(HttpHeaderNames.CONTENT_LENGTH, contentLength);

        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.write(response, ctx.voidPromise());
        }
    }

}
