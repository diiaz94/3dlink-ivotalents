package com.threedlink.ivotalents.common.domain;

import com.threedlink.ivotalents.common.interceptors.HttpInterceptor;
import com.threedlink.ivotalents.common.services.IvoService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diiaz94 on 12-06-2017.
 */
public class ServiceGenerator{

    //private static final String API_BASE_URL = "https://analytics.gsgtech.io:8443";
    public static final String API_BASE_URL = "http://demo4427111.mockable.io";

    // Interceptor for logging
    private  static final HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    // Build HTTP connection and indicate the interceptor
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpInterceptor())
            .addInterceptor(logginInterceptor);

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = builder
            .client(httpClient.build())
            .build();

    private static final IvoService IVO_SERVICE =  retrofit.create(IvoService.class);

    public ServiceGenerator(){}

    public static <S> S getService(Class<S> serviceClass) {

        if(serviceClass == IvoService.class){
            return (S) IVO_SERVICE;
        }

        return null;
    }

}