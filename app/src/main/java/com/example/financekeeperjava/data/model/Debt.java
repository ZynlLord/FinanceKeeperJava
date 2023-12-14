package com.example.financekeeperjava.data.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Debt implements Serializable {

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("isPaid")
    private boolean isPaid;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("amount")
    private int amount;

    @SerializedName("dueDate")
    private String dueDate;

    @SerializedName("name")
    private String name;

    @SerializedName("debtId")
    private int debtId;

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public boolean isIsPaid() {
        return isPaid;
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

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public int getDebtId() {
        return debtId;
    }
}