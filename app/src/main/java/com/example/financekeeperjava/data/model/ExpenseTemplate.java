package com.example.financekeeperjava.data.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ExpenseTemplate implements Serializable {

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("amount")
    private int amount;

    @SerializedName("idExpenseTemplate")
    private int idExpenseTemplate;

    @SerializedName("name")
    private String name;

    @SerializedName("idCategory")
    private int idCategory;

    @SerializedName("frequency")
    private String frequency;

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

    public void setIdExpenseTemplate(int idExpenseTemplate) {
        this.idExpenseTemplate = idExpenseTemplate;
    }

    public int getIdExpenseTemplate() {
        return idExpenseTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }
}