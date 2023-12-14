package com.example.financekeeperjava.data.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Notification implements Serializable {

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("isRead")
    private boolean isRead;

    @SerializedName("idNotification")
    private int idNotification;

    @SerializedName("sendAt")
    private String sendAt;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

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

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setSendAt(String sendAt) {
        this.sendAt = sendAt;
    }

    public String getSendAt() {
        return sendAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}