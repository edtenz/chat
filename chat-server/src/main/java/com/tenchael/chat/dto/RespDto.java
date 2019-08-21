package com.tenchael.chat.dto;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class RespDto {
    private Status status;
    private String errMessage;
    private Header header = new Header();
    private Map<String, Object> body = new HashMap<>();

    public RespDto() {
    }

    public RespDto(Header header, Map<String, Object> body) {
        this.header = header;
        this.body = body;
    }

    public RespDto(Status status, String errMessage, Header header, Map<String, Object> body) {
        this.status = status;
        this.errMessage = errMessage;
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
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
