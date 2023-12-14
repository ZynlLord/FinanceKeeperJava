package com.example.financekeeperjava.presentation.main.stats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.financekeeperjava.R;

public class PreStatsActivity extends AppCompatActivity {

    Button btn_by_dates_all, btn_all_stats, btn_all_no_cat, btn_date_no_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_stats);
        btn_all_no_cat = findViewById(R.id.btn_all_no_cat);
        btn_date_no_cat = findViewById(R.id.btn_date_no_cat);


        btn_all_no_cat.setOnClickListener(new View.OnClickListener() { // Переход на экран с диаграммой доходов и расходов за всё время
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
                startActivity(intent);
            }
        });
        btn_date_no_cat.setOnClickListener(new View.OnClickListener() { // Переход на экран с диаграммой доходов и расходов за всё время по категориям
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatsAllByCat.class);
                startActivity(intent);
            }
        });

    }
}