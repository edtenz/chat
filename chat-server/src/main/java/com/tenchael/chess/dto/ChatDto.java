package com.tenchael.chess.dto;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ChatDto {
    private Header header = new Header();
    private Map<String, Object> body = new HashMap<>();

    public ChatDto() {
    }

    public ChatDto(Header header, Map<String, Object> body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
