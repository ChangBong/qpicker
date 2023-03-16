package com.peopulley.core.common.enums;

public enum StatusEnum {

    FLAG_N(0, "N"),
    FLAG_Y(1, "Y"),

    USER_NORMAL(6, "일반 사용자"),
    USER_DEL(9, "삭제"),

    ;

    private int statusCode;
    private String statusMsg;

    StatusEnum(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMsg() {
        return this.statusMsg;
    }
}
