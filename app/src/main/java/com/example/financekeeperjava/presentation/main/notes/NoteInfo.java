package com.example.financekeeperjava.presentation.main.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Note;
import com.example.financekeeperjava.presentation.main.MenuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteInfo extends AppCompatActivity {

    EditText title_et, content_et;
    FloatingActionButton done_fab, delete_fab;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);
        apiInterface = API.buildRequest().create(ApiInterface.class);

        title_et = findViewById(R.id.info_note_title_et);
        content_et = findViewById(R.id.info_note_text_et);
        done_fab = findViewById(R.id.info_fab_success_note);
        delete_fab = findViewById(R.id.info_fab_delete_note);
        int idNote = getIntent().getIntExtra("noteId", 0);
        String creationDate = getIntent().getStringExtra("creationDate");
        title_et.setText(getIntent().getStringExtra("title"));
        content_et.setText(getIntent().getStringExtra("content"));

        delete_fab.setOnClickListener(new View.OnClickListener() { // Метод удаления заметки
            @Override
            public void onClick(View v) {
                Call<Void> deleteCall = apiInterface.deleteNote(idNote);
                deleteCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                            finish();
                            /*Toast.makeText(getApplicationContext(), "Заметка успешно удалена", Toast.LENGTH_SHORT).show();*/
                        } else {
                            Toast.makeText(getApplicationContext(), "Не удалось удалить замтеку", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("API", t.getMessage());
                    }
                });
            }
        });

        done_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_et.getText().toString();
                String content = content_et.getText().toString();
                Call<Note> updateCall = apiInterface.updateNote(idNote, new Note(
                        LoginFunctions.getLoggedUser().getIdUser(),
                        title,
                        creationDate,
                        idNote,
                        content
                ));
                updateCall.enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, Response<Note> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Заметка успешно изменена", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Не удалось обновить замтеку", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("API", t.getMessage());
                    }
                });
            }
        });

    }
}