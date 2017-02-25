package com.example.gedune.bookcollection.module;

import com.example.gedune.bookcollection.net.MyHttpCallback;

import okhttp3.Call;

/**
 * Created by gedune on 2/24/2017.
 */

public class BookDetailAction {

    public static void getBookkDetail(String result, final IGetBookCB cb ) {
        final BookDetailTask task = new BookDetailTask(result);
        task.enqueue(new MyHttpCallback() {
            @Override
            public void onResponse(Call call, Object data) {
                //实际的网络请求成功与否跟业务成功与否还得具体分析，在这里我们是业务成功了的。
                    if (cb != null) {
                        cb.onConnectSuccess(data);
                    }
            }

            @Override
            public void onFailure(Call call, Exception e) {
                //失败分两种，一种是 e 等于 null，那说明网络请求成功，那还得看 code 参数.
                if (cb !=null){
                    cb.onConnectFailed(e);
                }
            }
        },true);

    }

    public static interface IGetBookCB {
        public void onConnectSuccess(Object BookBean);

        public void onConnectFailed(Exception e);
    }

}
