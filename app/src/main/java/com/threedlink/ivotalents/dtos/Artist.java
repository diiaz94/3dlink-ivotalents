package com.threedlink.ivotalents.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by diiaz94 on 17-05-2017.
 */
public class Artist extends User {

    @SerializedName("nombre")
    @Expose
    private String usuario;

    @SerializedName("habilidad")
    @Expose
    private String habilidad;

    @SerializedName("fans_count")
    @Expose
    private String fansCount;

    @SerializedName("puntuacion")
    @Expose
    private float puntuacion;

}
