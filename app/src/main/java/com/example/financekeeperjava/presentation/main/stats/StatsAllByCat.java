package com.example.financekeeperjava.presentation.main.stats;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatsAllByCat extends AppCompatActivity {

    PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        chart = findViewById(R.id.chart);

        chart.getDescription().setEnabled(false);


    }

    private void updatePieChart(List<OperationCategorySummary> categorySummaries) {
        List<PieEntry> entries = new ArrayList<>();
        for (OperationCategorySummary summary : categorySummaries) {
            entries.add(new PieEntry(summary.getTotalAmount(), summary.getName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Категории операций");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(22f);
        PieData data = new PieData(dataSet);

        chart.setData(data);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setCenterText("Категории операций");
        chart.setCenterTextSize(14f);
        chart.setHoleRadius(40f);
        chart.setTransparentCircleRadius(45f);
        chart.animateY(1000);
        chart.invalidate();
    }

    private void getCategoriesAndOperationFromApi() {
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);

    }


    class OperationCategorySummary {
        private String name;
        private int totalAmount;

        public OperationCategorySummary(String name, int totalAmount) {
            this.name = name;
            this.totalAmount = totalAmount;
        }

        public String getName() {
            return name;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setType(String type) {
            this.name = type;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }
    }

}


