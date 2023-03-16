package com.peopulley.rest.common.enums;

public enum ServiceReturnMsgEnum {
    SUCCESS("정상 처리 됐습니다"),
    FALSE("오류가 발생했습니다"),
    IS_NOT_PRESENT("정보를 확인할 수 없습니다"),
    INVALID_ARGUMENT("잘못된 요청값 입니다"),
    UNAUTHORIZED("권한이 없습니다"),
    ROLE_NOT_PERMITTED("인증되지 않은 사용자 입니다")
    ;

    private String message;

    ServiceReturnMsgEnum(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}