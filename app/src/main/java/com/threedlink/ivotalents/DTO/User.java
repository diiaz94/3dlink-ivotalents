package com.threedlink.ivotalents.DTO;

/**
 * Created by diiaz94 on 19-03-2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("tipo_usuario")
    @Expose
    private String tipoUsuario;
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
     * @param tipoUsuario
     * @param token
     * @param email
     * @param usuario
     * @param password
     */
    public User(String tipoUsuario, String usuario, String email, String password, String token) {
        super();
        this.tipoUsuario = tipoUsuario;
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
                "tipoUsuario='" + tipoUsuario + '\'' +
                ", usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}