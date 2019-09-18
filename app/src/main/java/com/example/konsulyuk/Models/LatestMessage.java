package com.example.konsulyuk.Models;

public class LatestMessage {

    String chat_id, text, fromId, toId;
    Long timestamp;

    public LatestMessage(){}

    public LatestMessage(String chat_id, String text, String fromId, String toId, Long timestamp) {
        this.chat_id = chat_id;
        this.text = text;
        this.fromId = fromId;
        this.toId = toId;
        this.timestamp = timestamp;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
