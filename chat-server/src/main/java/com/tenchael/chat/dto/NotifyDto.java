package com.tenchael.chat.dto;

import java.util.List;

public class NotifyDto {

    private String content;
    private List<String> users;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
