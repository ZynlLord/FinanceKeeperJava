package com.example.financekeeperjava.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Operation implements Serializable {

    @SerializedName("date")
    private String date;

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("amount")
    private int amount;

    @SerializedName("idOperation")
    @Nullable
    private Integer idOperation; // Объект Integer может быть null

    @SerializedName("comment")
    private String comment;

    @SerializedName("type")
    private String type;

    @SerializedName("idWallet")
    private int idWallet;

    @SerializedName("idCategory")
    private int idCategory;

    public Operation(String date, int idUser, String createdAt, int amount, @Nullable Integer idOperation, String comment, String type, int idWallet, int idCategory) {
        this.date = date;
        this.idUser = idUser;
        this.createdAt = createdAt;
        this.amount = amount;
        this.idOperation = idOperation;
        this.comment = comment;
        this.type = type;
        this.idWallet = idWallet;
        this.idCategory = idCategory;
    }

    public String getDate() {
        return date;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getAmount() {
        return amount;
    }

    @Nullable
    public Integer getIdOperation() {
        return idOperation;
    }

    public String getComment() {
        return comment;
    }

    public String getType() {
        return type;
    }

    public int getIdWallet() {
        return idWallet;
    }

    public int getIdCategory() {
        return idCategory;
    }
}
