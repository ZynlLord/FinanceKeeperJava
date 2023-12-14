package com.example.financekeeperjava.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.func.ApiFunc;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.Note;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.presentation.main.operations.OperationInfoActivity;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Operation> operations;

    public OperationsAdapter(Context context, ArrayList<Operation> operations) {
        this.context = context;
        this.operations = operations;
    }

    public void updateData(ArrayList<Operation> newOperationsList) {
        operations.clear();
        operations.addAll(newOperationsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.operation_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Operation operation = operations.get(position);
        String price = String.valueOf(operation.getAmount());
        if (Objects.equals(operation.getType(), "Доход")) {
            holder.amount_tv.setText("+" + operation.getAmount());
        }
        else if (Objects.equals(operation.getType(), "Расход")) {
            holder.amount_tv.setText("-" + operation.getAmount());
        }
        else holder.amount_tv.setText("wepfio");

        String inputDate = operation.getDate();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]");
        LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
        dateTime = dateTime.plusDays(1);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));
        String outputDate = dateTime.format(outputFormatter);
        holder.date_tv.setText(outputDate);
        final String[] categoryName = {""};

        ApiFunc.getCategoryNameForOperation(operation.getIdOperation(), new ApiFunc.CategoryCallBack() {
            @Override
            public void onCategoryLoaded(Category category) {
                holder.category_name_tv.setText(category.getName());
                categoryName[0] = category.getName();
                Picasso.with(context).load(category.getIcon()).into(holder.imageView_category);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API", "Failed to load category name");
            }
        });

        holder.operation_lay.setOnClickListener(new View.OnClickListener() { // Обработка нажатия на катрочку операции
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OperationInfoActivity.class);
                intent.putExtra("idOperation", operation.getIdOperation());
                intent.putExtra("sumOperation", operation.getAmount());
                intent.putExtra("dateOperation", operation.getDate());
                intent.putExtra("categoryName", categoryName[0]);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return operations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder { // класс, который хранит ссылки на элементы списка для повторного использования.

        TextView category_name_tv;
        TextView amount_tv;
        TextView date_tv;
        ImageView imageView_category;
        CardView operation_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_name_tv = itemView.findViewById(R.id.category_name_tv);
            amount_tv = itemView.findViewById(R.id.amount_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            operation_lay = itemView.findViewById(R.id.operation_lay);
            imageView_category = itemView.findViewById(R.id.imageView_category);
        }
    }
}
