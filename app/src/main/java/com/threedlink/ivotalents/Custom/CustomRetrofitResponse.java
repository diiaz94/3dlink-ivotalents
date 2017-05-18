package com.threedlink.ivotalents.custom;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 10-05-2017.
 */
public class CustomRetrofitResponse<T>  {
    private ArrayList<T> list;
    public long id;
    public boolean isArrayList() {
        return list != null;
    }
}
