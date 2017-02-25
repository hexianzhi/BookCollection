package com.example.gedune.bookcollection.net;

import okhttp3.Call;

/**
 * Created by zhengwen on 16/12/6.
 */

public interface MyHttpCallback<T> {

    /***
     * 请求成功，httpcode 200,业务code ==1
     *
     * @param data
     */
    public void onResponse(Call call, Object data);


    /**
     * @param e
     */
    public void onFailure(Call call , Exception e);

}
