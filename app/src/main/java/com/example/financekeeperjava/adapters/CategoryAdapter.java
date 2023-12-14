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
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.Note;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mcontext;

    private ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.mcontext = context;
        this.categories = categories;
    }

    public void updateData(ArrayList<Category> newCategories) {
        categories.clear();
        categories.addAll(newCategories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.category_card, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.category_name_tv.setText(category.getName());
        Picasso.with(mcontext).load(category.getIcon()).into(holder.category_image);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder { // класс, который хранит ссылки на элементы списка для повторного использования.


        ImageView category_image;
        TextView category_name_tv;

        CardView card_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.image_catefory_iv);
            category_name_tv = itemView.findViewById(R.id.name_category_tv);
            card_lay = itemView.findViewById(R.id.card_category_lay);

        }
    }
}



