package com.threedlink.ivotalents.dtos;

/**
 * Created by diiaz94 on 19-03-2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User<T> {

    @SerializedName("tipo_usuario")
    @Expose
    private String role;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param role
     * @param token
     * @param email
     * @param usuario
     * @param password
     */
    public User(String role, String usuario, String email, String password, String token) {
        super();
        this.role = role;
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                ", usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}