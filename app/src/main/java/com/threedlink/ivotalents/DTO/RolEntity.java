package com.threedlink.ivotalents.DTO;

/**
 * Created by diiaz94 on 17-01-2017.
 */

public class RolEntity{
    String name;
    String category;
    String ability;
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
}
