package com.example.financekeeperjava.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Wallet implements Serializable {

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("balance")
    private int balance;

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    @SerializedName("currency")
    private String currency;

    @SerializedName("idWallet")
    @Nullable
    private Integer idWallet;

    public Wallet(int idUser, String createdAt, int balance, String name, String icon, String currency, Integer idWallet) {
        this.idUser = idUser;
        this.createdAt = createdAt;
        this.balance = balance;
        this.name = name;
        this.icon = icon;
        this.currency = currency;
        this.idWallet = idWallet;
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

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
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

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setIdWallet(int idWallet) {
        this.idWallet = idWallet;
    }

    public int getIdWallet() {
        return idWallet;
    }
}