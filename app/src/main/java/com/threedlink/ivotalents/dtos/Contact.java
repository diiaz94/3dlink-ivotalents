package com.threedlink.ivotalents.dtos;

/**
 * Created by diiaz94 on 29-01-2017.
 */
public class Contact {
    String name;
    String sector;
    int imageId;

    public Contact(String name, String sector, int imageId) {
        this.name = name;
        this.sector = sector;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
