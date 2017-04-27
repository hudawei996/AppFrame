package com.vanke.libvanke.net;


public class ApiException extends RuntimeException {
    private int code;
    private String message;
    private String content;

    public ApiException(int code, String message, String content) {
        super(message);
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }


    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
//            case USER_NOT_EXIST:
//                message = "该用户不存在";
//                break;
//            case WRONG_PASSWORD:
//                message = "密码错误";
//                break;
//            default:
//                message = "未知错误";
        }
        return message;
    }
}
