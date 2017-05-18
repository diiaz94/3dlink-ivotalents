package com.threedlink.ivotalents.dtos;

/**
 * Created by diiaz94 on 17-01-2017.
 */
public class Casting {
    String expiration;
    String category;
    String description;
    int imageId;

    public Casting() {

    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Casting(String category, String description, String expiration, int imageId) {
        this.expiration = expiration;
        this.description = description;
        this.category = category;
        this.imageId = imageId;
    }

}
