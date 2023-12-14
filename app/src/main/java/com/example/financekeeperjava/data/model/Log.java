package com.example.financekeeperjava.data.model;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Log implements Serializable {

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("action")
    private String action;

    @SerializedName("details")
    private String details;

    @SerializedName("idLog")
    @Nullable
    private Integer idLog;

    public Log(String timeStamp, int idUser, String action, String details, @Nullable Integer idLog) {
        this.timeStamp = timeStamp;
        this.idUser = idUser;
        this.action = action;
        this.details = details;
        this.idLog = idLog;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public int getIdLog() {
        return idLog;
    }
}