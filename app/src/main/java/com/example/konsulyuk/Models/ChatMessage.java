package com.example.konsulyuk.Models;

public class ChatMessage {
    String toId, fromId, idText, text, name_psikolog, name_pasien;
    Long timestamp = -1L;
    Integer view_type;
    int read_count;

    public static final int FROM_ROW = 0;
    public static final int TO_ROW = 1;

    public ChatMessage(){}

    public ChatMessage(String toId, String fromId, String idText, String text, String name_psikolog, String name_pasien, Long timestamp, int read_count ,int view_type) {
        this.toId = toId;
        this.fromId = fromId;
        this.idText = idText;
        this.text = text;
        this.name_psikolog = name_psikolog;
        this.name_pasien = name_pasien;
        this.timestamp = timestamp;
        this.read_count = read_count;
        this.view_type = view_type;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName_psikolog() {
        return name_psikolog;
    }

    public void setName_psikolog(String name_psikolog) {
        this.name_psikolog = name_psikolog;
    }

    public String getName_pasien() {
        return name_pasien;
    }

    public void setName_pasien(String name_pasien) {
        this.name_pasien = name_pasien;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }
}
