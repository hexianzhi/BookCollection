package com.example.gedune.bookcollection.net;


import android.os.Handler;
import android.util.Log;

import java.util.concurrent.Executor;

import okhttp3.Response;


/**
 * Created by zw on 16/8/3.
 */
public class ExecutorDelivery {
    /**
     * Used for posting responses, typically to the main thread.
     */
    private final Executor mResponsePoster;

    /**
     * Creates a new response delivery interface.
     *
     * @param handler {@link Handler} to post responses on
     */
    public ExecutorDelivery(final Handler handler) {
        // Make an Executor that just wraps the handler.
        mResponsePoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    public ExecutorDelivery(Executor executor) {
        mResponsePoster = executor;
    }


    public void postResponse(HttpCallback httpCallback, BaseRequest request, HttpResponse response, Exception e) {
        mResponsePoster.execute(ResponseDeliveryRunnable2.response(httpCallback, request, response, e));
    }

    public void postResponse(HttpCallback httpCallback,BaseRequest request,Response response,Exception e){
        mResponsePoster.execute(ResponseDeliveryRunnable3.response(httpCallback, request, response, e));
    }

    private static class ResponseDeliveryRunnable3 implements Runnable {
        private final BaseRequest request;
        private final Exception e;
        private Response response;
        private HttpCallback callback;

        private ResponseDeliveryRunnable3(HttpCallback callback, BaseRequest request, Response response, Exception e) {
            this.callback = callback;
            this.request = request;
            this.response = response;
            this.e = null;
        }


        public static ExecutorDelivery.ResponseDeliveryRunnable3 response(HttpCallback modelCallback, BaseRequest request, Response response, Exception e) {
            return new ExecutorDelivery.ResponseDeliveryRunnable3(modelCallback, request, response, e);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            Log.e("fuck","onResponse response.code :"+ response.code());
                if (e == null && response.code() == ResponseCode.sSUCCESS) {
                    this.callback.onResponse(request, response.body());
                } else {
                    this.callback.onFailure(request, e, response.code(), response.message());
                }
        }
    }

    private static class ResponseDeliveryRunnable2 implements Runnable {
        private final BaseRequest request;
        private final Exception e;
        private HttpResponse httpResponse;
        private HttpCallback callback;

        private ResponseDeliveryRunnable2(HttpCallback callback, BaseRequest request, HttpResponse response, Exception e) {
            this.callback = callback;
            this.request = request;
            this.e = null;
            this.httpResponse = response;
        }


        public static ExecutorDelivery.ResponseDeliveryRunnable2 response(HttpCallback modelCallback, BaseRequest request, HttpResponse response, Exception e) {
            return new ExecutorDelivery.ResponseDeliveryRunnable2(modelCallback, request, response, e);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            if (e == null && httpResponse.code == ResponseCode.SUCCESS) {
                this.callback.onResponse(request, httpResponse.data);
            } else {
                this.callback.onFailure(request, e, httpResponse.code, httpResponse.message);
            }
        }
    }


}
