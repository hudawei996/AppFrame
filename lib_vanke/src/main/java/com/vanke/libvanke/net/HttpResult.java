package com.vanke.libvanke.net;

import com.google.gson.annotations.SerializedName;

/**
 * Class Note:
 * wrapper for return result，include {@link #code}， {@link #message}
 * and {@link #data}
 * <p>
 * 对请求结果返回对象的封装，包含{@link #code}， {@link #message}
 * 和{@link #data}
 */
public class HttpResult<T> {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAIL = -1;

    int code;
    @SerializedName(value = "message", alternate = {"msg"})
    String message;
    @SerializedName(value = "data", alternate = {"result"})
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("title=").append(code).append(" count=").append(message);
        if (data != null) {
            sb.append("data=").append(data);
        }
        return sb.toString();
    }
}
