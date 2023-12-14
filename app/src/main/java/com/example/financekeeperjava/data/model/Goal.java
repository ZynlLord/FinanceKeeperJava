package com.example.financekeeperjava.data.model;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Goal implements Serializable {

    @SerializedName("currentProgress")
    private int currentProgress;

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("idGoal")
    @Nullable
    private Integer idGoal;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("targetDate")
    private String targetDate;

    @SerializedName("name")
    private String name;

    @SerializedName("targetSum")
    private int targetSum;

    @SerializedName("currentAmount")
    private int currentAmount;

    @SerializedName("isAchieved")
    private boolean isAchieved;

    public Goal(int currentProgress, int idUser, @Nullable Integer idGoal, String createdAt, String targetDate, String name, int targetSum, int currentAmount, boolean isAchieved) {
        this.currentProgress = currentProgress;
        this.idUser = idUser;
        this.idGoal = idGoal;
        this.createdAt = createdAt;
        this.targetDate = targetDate;
        this.name = name;
        this.targetSum = targetSum;
        this.currentAmount = currentAmount;
        this.isAchieved = isAchieved;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdGoal(int idGoal) {
        this.idGoal = idGoal;
    }

    public int getIdGoal() {
        return idGoal;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTargetSum(int targetSum) {
        this.targetSum = targetSum;
    }

    public int getTargetSum() {
        return targetSum;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setIsAchieved(boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

    public boolean isIsAchieved() {
        return isAchieved;
    }
}