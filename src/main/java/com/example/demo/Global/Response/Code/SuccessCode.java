package com.example.demo.Global.Response.Code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    //Phone
    PHONE_SEND_SUCCESS("P001","코드 생성하여 인증번호 전송."),
    PHONE_CHECK_SUCCESS("P002", "휴대폰 번호 인증 확인."),

    //User
    USER_PROFILE_SUCCESS( "U001", "프로필 조회 완료."),

    //Trade(사례금 전달)


    //Post
    POST_CREATE_SUCCESS( "P001", "게시글 등록 완료.")

    ;
    private final String code;
    private final String message;
}
