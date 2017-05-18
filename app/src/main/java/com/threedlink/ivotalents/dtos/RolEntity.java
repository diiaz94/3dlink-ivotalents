package com.threedlink.ivotalents.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by diiaz94 on 17-01-2017.
 */

public class RolEntity extends User{
    @SerializedName("nombre")
    @Expose
    String name;

    String category;

    @SerializedName("habilidad")
    @Expose
    String ability;

    @SerializedName("fans_count")
    @Expose
    private int fansCount;

    @SerializedName("puntuacion")
    @Expose
    private float puntuacion;

    int imageId;

    public RolEntity( String category,String name, String ability,int imageId) {
        this.name = name;
        this.category = category;
        this.ability = ability;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }
}
