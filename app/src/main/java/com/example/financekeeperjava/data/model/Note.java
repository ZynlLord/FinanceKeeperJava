package com.example.financekeeperjava.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Note implements Serializable {


    @SerializedName("idUser")
    private int idUser;

    @SerializedName("title")
    private String title;

    @SerializedName("creationDate")
    private String creationDate;

    @SerializedName("idNote")
    @Nullable
    private Integer idNote = 0;

    @SerializedName("content")
    private String content;

    public Note(int idUser, String title, String creationDate, Integer idNote, String content) {
        this.idUser = idUser;
        this.title = title;
        this.creationDate = creationDate;
        this.idNote = idNote;
        this.content = content;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}