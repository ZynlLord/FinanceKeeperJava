package com.example.financekeeperjava.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Category implements Serializable {

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    @SerializedName("idCategory")
    @Nullable
    private Integer idCategory;

    public Category(String createdAt, String name, String icon, @Nullable Integer idCategory) {
        this.createdAt = createdAt;
        this.name = name;
        this.icon = icon;
        this.idCategory = idCategory;
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

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }
}