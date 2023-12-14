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

public class AddNoteActivity extends AppCompatActivity {

    FloatingActionButton fab_done_note, fab_not_done_note;
    EditText title_et, content_et;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        apiInterface = API.buildRequest().create(ApiInterface.class);
        fab_done_note = findViewById(R.id.fab_done_note);
        fab_not_done_note = findViewById(R.id.fab_not_done_note);
        title_et = findViewById(R.id.add_note_title_et);
        content_et = findViewById(R.id.add_note_text_et);

        fab_done_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_et.getText().toString();
                String content = content_et.getText().toString();

                if (!title.equals("")) {
                    Call<Note> noteCall = apiInterface.postNote(new Note(
                            LoginFunctions.getLoggedUser().getIdUser(),
                            title,
                            null,
                            null,
                            content
                    ));
                    noteCall.enqueue(new Callback<Note>() {
                        @Override
                        public void onResponse(Call<Note> call, Response<Note> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                                finish();
/*
                            Toast.makeText(getApplicationContext(), "Заметка успешно создана!", Toast.LENGTH_LONG).show();
*/
                            } else {
                                Toast.makeText(getApplicationContext(), "Ошибка при создании заметки", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Note> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("API", t.getMessage());
                        }
                    });
                }
                else
                    Toast.makeText(AddNoteActivity.this, "Название заметки не может быть пустым", Toast.LENGTH_SHORT).show();
            }
        });

        fab_not_done_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}