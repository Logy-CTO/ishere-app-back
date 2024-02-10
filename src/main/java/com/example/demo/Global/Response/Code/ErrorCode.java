package com.example.demo.Global.Response.Code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Phone
    INVALID_PHONE("P001", "휴대폰 번호 오류"),
    INVALID_CODE("P002", "코드 입력 오류"),
    TIME_LIMIT("P003", "시간 초과"),

    //Global
    INTERNAL_SERVER_ERROR("G001", "내부 서버 오류"),
    NOT_ALLOWED_METHOD("G002", "허용 되지 않은 HTTP method"),
    INVALID_INPUT_VALUE("G003", "검증 되지 않은 입력"),

    //User
    MEMBER_NOT_FOUND("U001", "존재 하지 않는 사용자"),
    NO_AUTHORITY("U002", "권한이 없음"),

    //POST

    //TRADE(사례금 전달)
    ;


    private final String code;
    private final String message;
}