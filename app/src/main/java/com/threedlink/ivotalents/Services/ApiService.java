package com.threedlink.ivotalents.Services;

import com.threedlink.ivotalents.DTO.Ticket;
import com.threedlink.ivotalents.DTO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by diiaz94 on 19-03-2017.
 */
public interface ApiService {

    public static final String BASE_URL = "http://201.210.145.206:8000/api/v1/";
    //public static final String BASE_URL = "http://demo4427111.mockable.io/";

    @Headers({"Content-Type: application/json"})
    @POST("login/")
    Call<Ticket> login(@Body User user);

    @Headers({"Content-Type: application/json"})
    @POST("registro/")
    Call<Ticket> register(@Body User user);

    @Headers({"Content-Type: application/json"})
    @GET("talentos")
    Call<List<User>> talents(@Header("Authorizacion") String JWT);

}

