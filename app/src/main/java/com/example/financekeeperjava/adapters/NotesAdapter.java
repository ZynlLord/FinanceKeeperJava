package com.example.financekeeperjava.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.model.Note;
import com.example.financekeeperjava.presentation.main.notes.NoteInfo;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Note> notesList;

    public NotesAdapter(Context context, ArrayList<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    // Добавьте этот метод для обновления данных адаптера
    public void updateData(ArrayList<Note> newNotesList) {
        notesList.clear();
        notesList.addAll(newNotesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.text_tv.setText(note.getContent());
        holder.title_tv.setText(note.getTitle());
        holder.note_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteInfo.class);
                intent.putExtra("noteId", note.getIdNote());
                intent.putExtra("creationDate", note.getCreationDate());
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getContent());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder { // класс, который хранит ссылки на элементы списка для повторного использования.

        TextView title_tv, text_tv;
        CardView note_cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            text_tv = itemView.findViewById(R.id.text_tv);
            note_cardview = itemView.findViewById(R.id.note_cardview);

        }
    }

}
