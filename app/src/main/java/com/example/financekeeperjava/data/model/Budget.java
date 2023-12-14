package com.example.financekeeperjava.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Budget implements Serializable {

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("amount")
    private int amount;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("idBudget")
    private int idBudget;

    @SerializedName("startDate")
    private String startDate;

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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setIdBudget(int idBudget) {
        this.idBudget = idBudget;
    }

    public int getIdBudget() {
        return idBudget;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }
}