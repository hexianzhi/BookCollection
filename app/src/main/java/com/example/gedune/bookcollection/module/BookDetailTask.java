package com.example.gedune.bookcollection.module;

import com.alibaba.fastjson.JSON;
import com.example.gedune.bookcollection.Bean.BookBean;
import com.example.gedune.bookcollection.net.HttpMethod;
import com.example.gedune.bookcollection.net.JSONRequest;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by gedune on 2017/2/4.
 */

public class BookDetailTask extends JSONRequest {
    private String mIsbn;

    public BookDetailTask(String isbn ) {
        super( );
        mIsbn = isbn;
    }

    @Override
    public String getApi() {
        return "/isbn/" + mIsbn;
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Class getModelClass() {
        return BookBean.class;
    }

    @Override
    public Object parseResponse(ResponseBody body) throws IOException {
        BookBean bookBean = JSON.parseObject(body.string(),BookBean.class);
        return bookBean;
    }
}
