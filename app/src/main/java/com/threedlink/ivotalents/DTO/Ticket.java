package com.threedlink.ivotalents.DTO;

/**
 * Created by diiaz94 on 16-03-2017.
 */
public class Ticket {
    private String token;

    public Ticket(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
