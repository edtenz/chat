package com.tenchael.chat.server;

import com.tenchael.chat.config.Constants;
import com.tenchael.chat.dto.NotifyDto;
import com.tenchael.chat.dto.RespDto;
import com.tenchael.chat.dto.Status;
import com.tenchael.chat.utils.BeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class NotifyProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyProcessor.class);

    @Override
    public void submitRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        String token = request.headers().get("token");
        LOGGER.debug("token: {}", token);
        String content = request.content().toString(Constants.ENCODING);
        LOGGER.debug("request content: {}", content);
        NotifyDto notifyDto = BeanUtils.jsonToObject(content, NotifyDto.class);

        String respContent = null;
        if (notifyDto == null) {
            //user not exists
            LOGGER.warn("no notification is needed");
            RespDto respDto = new RespDto();
            respDto.setStatus(Status.notOk);
            respDto.setErrMessage("no notification is needed");
            respContent = BeanUtils.objectToJson(respDto);
        } else {
            LOGGER.info("notify to {}", notifyDto.getUsers());
            RespDto respDto = new RespDto();
            respDto.setStatus(Status.ok);
            Map<String, Object> body = new HashMap<>();
            body.put("content", notifyDto.getContent());
            respDto.setBody(body);
            respContent = BeanUtils.objectToJson(respDto);
        }

        LOGGER.debug("response content: {}", respContent);
        writeResponse(ctx, request, respContent);

    }
}
