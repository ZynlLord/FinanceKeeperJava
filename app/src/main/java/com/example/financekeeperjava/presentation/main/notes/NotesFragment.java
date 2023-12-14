package com.example.financekeeperjava.presentation.main.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.adapters.NotesAdapter;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesFragment extends Fragment {

    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private Context mContext; // Переменная для хранения контекста активности
    private NotesAdapter adapter; // Адаптер

    private FloatingActionButton fab;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context; // Сохраняем контекст активности
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.notes_recycler);
        fab = view.findViewById(R.id.fab_add_note);

        // Создаем пустой адаптер и устанавливаем его
        adapter = new NotesAdapter(mContext, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        apiInterface = API.buildRequest().create(ApiInterface.class);
        Call<ArrayList<Note>> getNotesList = apiInterface.getNotesList();

        getNotesList.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Note> notees = new ArrayList<>();
                    if (response.body() != null) {
                        for (Note note : response.body()) {
                            if (note.getIdUser() == LoginFunctions.getLoggedUser().getIdUser()) {
                                notees.add(note);
                            }
                        }
                    }

                    adapter.updateData(notees); // Обновляем адаптер данными
                } else {
                    Toast.makeText(mContext, "Ошибка со стороны клиента", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }
}

