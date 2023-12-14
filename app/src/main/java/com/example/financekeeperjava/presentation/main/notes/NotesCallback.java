package com.example.financekeeperjava.presentation.main.notes;

import com.example.financekeeperjava.data.model.Note;

import java.util.ArrayList;

public interface NotesCallback {
    void onNotesLoaded(ArrayList<Note> notes);
    void onError(String errorMessage);
}


