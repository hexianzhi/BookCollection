package com.example.gedune.bookcollection.net;

/**
 * HttpResonse<T> WTF??
 * Created by zw on 16/8/3.
 */
public class HttpResponse<T> {
    public static final int sNOT_FOUND = 404;

    public Integer code;
    public String message;
    public T data;

    public HttpResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpResponse() {
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
