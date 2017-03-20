package com.threedlink.ivotalents.Services;

import com.threedlink.ivotalents.DTO.Ticket;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by diiaz94 on 16-03-2017.
 */
public interface LoginService {


    @POST("login")
    Call<Ticket> token();

}
