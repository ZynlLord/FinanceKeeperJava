package com.example.financekeeperjava.presentation.main.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.ApiFunc;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.presentation.main.notes.NotesCallback;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Note;
import com.example.financekeeperjava.presentation.main.operations.OperationsCallBack;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClearingActivity extends AppCompatActivity {

    Button notes_clearing, clear_all_operations_btn;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearing);
        notes_clearing = findViewById(R.id.clear_all_notes_btn);
        clear_all_operations_btn = findViewById(R.id.clear_all_operations_btn);
        apiInterface = API.buildRequest().create(ApiInterface.class);

        notes_clearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idUser = LoginFunctions.getLoggedUser().getIdUser();

                // Получить все заметки пользователя
                getAllNotes(idUser, new NotesCallback() {
                    @Override
                    public void onNotesLoaded(ArrayList<Note> notes) {
                        // Загрузка заметок завершена
                        // Теперь можно удалить их
                        deleteNotes(idUser, notes);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        clear_all_operations_btn.setOnClickListener(new View.OnClickListener() { // Обработка нажатия на кнопку "Очистить историю операций"
            @Override
            public void onClick(View v) {
                int idUser = LoginFunctions.getLoggedUser().getIdUser();

                getAllOperations(idUser, new OperationsCallBack() {
                    @Override
                    public void onOperationsLoaded(ArrayList<Operation> operations) {
                        deleteOperations(idUser, operations);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Получить все заметки пользователя по idUser
    public void getAllNotes(int idUser, final NotesCallback callback) {
        Call<ArrayList<Note>> noteCall = apiInterface.getNotesList();
        noteCall.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Note> notes = response.body();
                    callback.onNotesLoaded(notes);
                } else {
                    callback.onError("Ошибка при загрузке заметок");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getAllOperations(int idUser, final OperationsCallBack callback) {
        Call<ArrayList<Operation>> operationCall = apiInterface.getOperationsList();
        operationCall.enqueue(new Callback<ArrayList<Operation>>() {
            @Override
            public void onResponse(Call<ArrayList<Operation>> call, Response<ArrayList<Operation>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Operation> operations = response.body();
                    callback.onOperationsLoaded(operations);
                } else {
                    callback.onError("Ошибка при загрузке операций");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Operation>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Удалить заметки пользователя
    public void deleteNotes(int idUser, ArrayList<Note> notes) {
        // Создать список для хранения заметок, которые нужно удалить
        ArrayList<Note> notesToDelete = new ArrayList<>();

        // Перебрать все заметки и добавить в список те, которые соответствуют idUser
        for (Note note : notes) {
            if (note.getIdUser() == idUser) {
                notesToDelete.add(note);
            }
        }

        // Удалить заметки из базы данных или хранилища
        for (Note note : notesToDelete) {
            Call<Void> deleteCall = apiInterface.deleteNote(note.getIdNote());
            deleteCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Заметки успешно удалены
                        Toast.makeText(getApplicationContext(), "Заметки удалены", Toast.LENGTH_SHORT).show();
                    } else {
                        // Ошибка при удалении заметок
                        Toast.makeText(getApplicationContext(), "Ошибка при удалении заметок", Toast.LENGTH_SHORT).show();
                        int statusCode = response.code(); // Получение кода состояния HTTP
                        String errorMessage = response.message(); // Получение текста ошибки от сервера (если есть)
                        Log.e("API", "HTTP Status Code: " + statusCode);
                        Log.e("API", "Error Message: " + errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Обработка ошибки при выполнении запроса
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void deleteOperations(int idUser, ArrayList<Operation> operations) {
        // Создать список для хранения заметок, которые нужно удалить
        ArrayList<Operation> operationToDelete = new ArrayList<>();

        // Перебрать все заметки и добавить в список те, которые соответствуют idUser
        for (Operation operation : operations) {
            if (operation.getIdUser() == idUser) {
                operationToDelete.add(operation);
            }
        }

        for (Operation operation : operationToDelete) {
            Call<Void> deleteOperation = apiInterface.deleteOperation(operation.getIdOperation());
            deleteOperation.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Заметки успешно удалены
                        Toast.makeText(getApplicationContext(), "Операции удалены", Toast.LENGTH_SHORT).show();
                    } else {
                        // Ошибка при удалении заметок
                        Toast.makeText(getApplicationContext(), "Ошибка при удалении операций", Toast.LENGTH_SHORT).show();
                        int statusCode = response.code(); // Получение кода состояния HTTP
                        String errorMessage = response.message(); // Получение текста ошибки от сервера (если есть)
                        Log.e("API", "HTTP Status Code: " + statusCode);
                        Log.e("API", "Error Message: " + errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Обработка ошибки при выполнении запроса
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
