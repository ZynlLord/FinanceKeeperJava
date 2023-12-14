package com.example.financekeeperjava.adapters;

import static java.lang.String.format;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.TypedArrayUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.func.WalletFunc;
import com.example.financekeeperjava.data.model.Goal;
import com.example.financekeeperjava.data.model.Note;
import com.example.financekeeperjava.presentation.main.notes.NoteInfo;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Goal> goalArrayList;


    public GoalsAdapter(Context context, ArrayList<Goal> goalArrayList) {
        this.context = context;
        this.goalArrayList = goalArrayList;
    }

    public void updateData(ArrayList<Goal> newGoalList) {
        goalArrayList.clear();
        goalArrayList.addAll(newGoalList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goal_card, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goal goal = goalArrayList.get(position);
        holder.goal_name_tv.setText(goal.getName());
        int targetSum = goal.getTargetSum();
        if (WalletFunc.isActiveWalletExists()) {
            int balance = WalletFunc.getActiveWallet().getBalance();
            int percent = (balance * 100) / goal.getTargetSum();
            String text = String.format("%d (%d%%)", balance, percent);
            holder.minValueText.setText(text);
        }
        else {
            holder.minValueText.setText("Сначала надо выбрать активный кошелёк!");
        }
        holder.targetValue_tv.setText(String.valueOf(targetSum));
        String inputDate = goal.getTargetDate();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]");
        LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));
        String outputDate = dateTime.format(outputFormatter);
        holder.targetDate_tv.setText(format("До: %s", outputDate));

    }

    @Override
    public int getItemCount() {
        return goalArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder { // класс, который хранит ссылки на элементы списка для повторного использования.
        TextView goal_name_tv, targetValue_tv, targetDate_tv, minValueText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goal_name_tv = itemView.findViewById(R.id.goal_name_tv);
            targetValue_tv = itemView.findViewById(R.id.targetValue_tv);
            targetDate_tv = itemView.findViewById(R.id.targetDate_tv);
            minValueText = itemView.findViewById(R.id.minValueText);

        }
    }
}
