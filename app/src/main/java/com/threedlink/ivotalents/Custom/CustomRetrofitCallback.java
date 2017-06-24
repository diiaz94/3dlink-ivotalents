package com.threedlink.ivotalents.custom;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


import com.threedlink.ivotalents.common.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diiaz94 on 08-05-2017.
 */
public abstract class CustomRetrofitCallback<T> implements Callback<T> {

    private static final String TAG = "CustomRetrofitCallback" ;
    public CustomRetrofitCallback() {

    }



    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()){
            handleSuccess(response);
        }else{
            handleError(response);
            Log.v(TAG, "Response not successfully" + response.errorBody());
        }
        Log.i(TAG,"Response message"+response.message());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Util.showMessage("Solicitud fallida, revisa tu conexion a internet.");
    }

    public abstract void handleSuccess(Response response);
    public abstract void handleError(Response response);




}
