package com.tenchael.chess.dto;

import com.alibaba.fastjson.JSON;

public class Header {
    private Type type;
    private String roomId;
    private String clientId;
    private String token;
    private Operation operation;

    public Header() {
    }

    public Header(Header header) {
        setType(header.getType());
        setRoomId(header.getRoomId());
        setClientId(header.getClientId());
        setToken(header.getToken());
        setOperation(header.getOperation());
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
