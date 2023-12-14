package com.example.financekeeperjava.data.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class IncomeSource implements Serializable {

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    @SerializedName("idIncomeSource")
    private int idIncomeSource;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIdIncomeSource(int idIncomeSource) {
        this.idIncomeSource = idIncomeSource;
    }

    public int getIdIncomeSource() {
        return idIncomeSource;
    }
}