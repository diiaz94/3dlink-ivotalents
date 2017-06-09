package com.threedlink.ivotalents.services;

import com.threedlink.ivotalents.dtos.Artist;
import com.threedlink.ivotalents.dtos.Casting;
import com.threedlink.ivotalents.dtos.MediaResource;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.dtos.Ticket;
import com.threedlink.ivotalents.dtos.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by diiaz94 on 19-03-2017.
 */
public interface ApiService {

   //public static final String BASE_URL = "http://201.210.145.206:8000/api/v1/";
   public static final String BASE_URL = "http://demo4427111.mockable.io/";

    @Headers({"Content-Type: application/json"})
    @POST("login")
    Call<Ticket> login(@Body User user);

    @Headers({"Content-Type: application/json"})
    @POST("registro/")
    Call<Ticket> register(@Body User user);

    @Headers({"Content-Type: application/json"})
    @GET("talentos")
    Call<List<User>> talents(@Header("Authorizacion") String JWT);

    @Headers({"Content-Type: application/json"})
    @GET("castings")
    Call<ArrayList<Casting>> castings(@Header("Authorizacion") String JWT);

    @Headers({"Content-Type: application/json"})
    @GET("providers")
    Call<ArrayList<RolEntity>> providers(@Header("Authorizacion") String JWT);

    @Headers({"Content-Type: application/json"})
    @GET("participations")
    Call<ArrayList<Casting>> participations(@Header("Authorizacion") String JWT);

    @Headers({"Content-Type: application/json"})
    @GET("user")
    Call<RolEntity> general_information(@Header("Authorizacion") String JWT,@Query("email") String email);


    @Headers({"Content-Type: application/json"})
    @GET("media-resources")

    Call<ArrayList<MediaResource>> mediaResources(@Header("Authorizacion") String JWT, @Query("email") String email);

}

