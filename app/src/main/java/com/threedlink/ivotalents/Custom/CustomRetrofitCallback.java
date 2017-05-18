package com.threedlink.ivotalents.custom;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diiaz94 on 08-05-2017.
 */
public abstract class CustomRetrofitCallback<T> implements Callback<T> {

    private static final String TAG = "CustomRetrofitCallback" ;
    private Activity activity;
    public CustomRetrofitCallback(Activity activity) {
    this.activity = activity;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()){
                handleSuccess(response);
        }else{
            handleLogicError(response.body());
            Log.v(TAG, "Response not successfully" + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(activity,"Por favor revise su conexion a internet...",Toast.LENGTH_SHORT).show();
        failure(t.getCause());
    }

    protected abstract void handleSuccess(Response response);
    public abstract void failure(Throwable error);

    void handleLogicError(T o) {
        Log.v(TAG, "Error because userId is " );

    }


}
