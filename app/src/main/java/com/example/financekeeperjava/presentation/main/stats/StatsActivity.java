package com.example.financekeeperjava.presentation.main.stats;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Operation;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {

    PieChart chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        chart = findViewById(R.id.chart);


        chart.getDescription().setEnabled(false);

        // Получите данные операций и обновите PieChart
        getOperationsFromApi();
    }

    private void updatePieChart(List<OperationTypeSummary> typeSummaries) {
        List<PieEntry> entries = new ArrayList<>();
        for (OperationTypeSummary summary : typeSummaries) {
            entries.add(new PieEntry(summary.getTotalAmount(), summary.getType()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Типы операций");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(22f);
        PieData data = new PieData(dataSet);

        chart.setData(data);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setCenterText("Типы операций");
        chart.setCenterTextSize(14f);
        chart.setHoleRadius(40f);
        chart.setTransparentCircleRadius(45f);
        chart.animateY(1000);
        chart.invalidate();
    }

    private void getOperationsFromApi() {
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        final List<OperationTypeSummary> typeSummaries = new ArrayList<>();
        Call<ArrayList<Operation>> getOperations = apiInterface.getOperationsList();
        getOperations.enqueue(new Callback<ArrayList<Operation>>() {
            @Override
            public void onResponse(Call<ArrayList<Operation>> call, Response<ArrayList<Operation>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Operation> operations = response.body();
                        for (Operation op : operations) {
                            if (op.getIdUser() == LoginFunctions.getLoggedUser().getIdUser()) {
                                // Поиск соответствующего типа операции в списке typeSummaries
                                boolean found = false;
                                for (OperationTypeSummary summary : typeSummaries) {
                                    if (summary.getType().equals(op.getType())) {
                                        // Обновление totalAmount для найденного типа
                                        int totalAmount = summary.getTotalAmount() + op.getAmount();
                                        summary.setTotalAmount(totalAmount);
                                        found = true;
                                        break;
                                    }
                                }
                                // Если тип операции не найден, создайте новый элемент OperationTypeSummary
                                if (!found) {
                                    typeSummaries.add(new OperationTypeSummary(op.getType(), op.getAmount()));
                                }
                            }
                        }
                        updatePieChart(typeSummaries);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка со стороны клиента", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Operation>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });
    }
}

    class OperationTypeSummary {
    private String type;
    private int totalAmount;

    public OperationTypeSummary(String type, int totalAmount) {
        this.type = type;
        this.totalAmount = totalAmount;
    }

    public String getType() {
        return type;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}

