package com.acv.finalproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vinicius.castro on 08/03/2017.
 */

public class User implements Serializable{

    private Long id;

    @SerializedName("usuario")
    private String username;

    @SerializedName("senha")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
