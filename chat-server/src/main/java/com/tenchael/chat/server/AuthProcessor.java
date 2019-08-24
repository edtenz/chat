package com.tenchael.chat.server;

import com.tenchael.chat.config.Constants;
import com.tenchael.chat.dto.AuthDto;
import com.tenchael.chat.dto.RespDto;
import com.tenchael.chat.dto.Status;
import com.tenchael.chat.utils.BeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthProcessor.class);

    private final Map<String, String> userMap = new HashMap<>();


    public AuthProcessor() {
        //TODO use DB repository user info
        userMap.put("test", "123456");
        userMap.put("root", "root");
        userMap.put("teng", "teng");
    }

    @Override
    public void submitRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        String content = request.content().toString(Constants.ENCODING);
        AuthDto auth = BeanUtils.jsonToObject(content, AuthDto.class);
        String upass = userMap.get(auth.getUsername());

        String respContent = null;
        if (upass == null) {
            //user not exists
            LOGGER.warn("user {} does not exists", auth.getUsername());
            RespDto respDto = new RespDto();
            respDto.setStatus(Status.notOk);
            respDto.setErrMessage(String.format("user[%s] not exists", auth.getUsername()));
            respContent = BeanUtils.objectToJson(respDto);
        } else if (!upass.equals(auth.getPassword())) {
            LOGGER.warn("user {} input wrong password", auth.getUsername());
            RespDto respDto = new RespDto();
            respDto.setStatus(Status.notOk);
            respDto.setErrMessage("password wrong");
            respContent = BeanUtils.objectToJson(respDto);
        } else {
            LOGGER.info("user {} auth success", auth.getUsername());
            RespDto respDto = new RespDto();
            respDto.setStatus(Status.ok);
            Map<String, Object> body = new HashMap<>();
            body.put("token", UUID.randomUUID().toString());
            respDto.setBody(body);
            respContent = BeanUtils.objectToJson(respDto);
        }

        LOGGER.debug("response content: {}", respContent);
        writeResponse(ctx, request, respContent);
    }


}
