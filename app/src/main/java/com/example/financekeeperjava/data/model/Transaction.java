package com.example.financekeeperjava.data.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Transaction implements Serializable {

    @SerializedName("date")
    private String date;

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("amount")
    private int amount;

    @SerializedName("comment")
    private String comment;

    @SerializedName("type")
    private String type;

    @SerializedName("idWallet")
    private int idWallet;

    @SerializedName("idCategory")
    private int idCategory;

    @SerializedName("idTransaction")
    private int idTransaction;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setIdWallet(int idWallet) {
        this.idWallet = idWallet;
    }

    public int getIdWallet() {
        return idWallet;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public int getIdTransaction() {
        return idTransaction;
    }
}