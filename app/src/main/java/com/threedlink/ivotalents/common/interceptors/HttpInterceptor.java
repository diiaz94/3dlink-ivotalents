package com.threedlink.ivotalents.common.interceptors;



import com.threedlink.ivotalents.common.util.Util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
public class HttpInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();
                //.header("Authorization", Util.getToken()); // <-- this is the important line
        Request request = requestBuilder.build();
        okhttp3.Response response = chain.proceed(request);

        return response;
    }
}