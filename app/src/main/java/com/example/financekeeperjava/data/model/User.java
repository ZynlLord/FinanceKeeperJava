package com.example.financekeeperjava.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable {

    @SerializedName("idUser")
    @Nullable
    private Integer idUser = 0;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    @SerializedName("surname")
    private String surname;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("login")
    private String login;

    public User(Integer idUser, String createdAt, String password, String role, String surname, String name, String avatar, String login) {
        this.idUser = idUser;
        this.createdAt = createdAt;
        this.password = password;
        this.role = role;
        this.surname = surname;
        this.name = name;
        this.avatar = avatar;
        this.login = login;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}