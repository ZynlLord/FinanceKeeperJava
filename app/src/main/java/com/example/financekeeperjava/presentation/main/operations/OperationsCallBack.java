package com.example.financekeeperjava.presentation.main.operations;

import com.example.financekeeperjava.data.model.Note;
import com.example.financekeeperjava.data.model.Operation;

import java.util.ArrayList;

public interface OperationsCallBack {
    void onOperationsLoaded(ArrayList<Operation> operations);
    void onError(String errorMessage);
}
