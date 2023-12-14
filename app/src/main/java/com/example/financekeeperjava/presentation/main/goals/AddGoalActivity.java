package com.example.financekeeperjava.presentation.main.goals;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Goal;
import com.example.financekeeperjava.presentation.main.MenuActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGoalActivity extends AppCompatActivity {


    EditText editTextGoalName, editTextGoalAmount, editTextGoalDate;

    private Button buttonSelectDate, buttonSave;


    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);

        editTextGoalName = findViewById(R.id.editTextGoalName);
        editTextGoalAmount = findViewById(R.id.editTextGoalAmount);
        editTextGoalDate = findViewById(R.id.editTextGoalDate);

        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        buttonSave = findViewById(R.id.buttonAddGoal);



        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputDate = editTextGoalDate.getText().toString(); // "3.9.2023"
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

                String formattedDate = null;
                try {
                    Date date = inputFormat.parse(inputDate); // Преобразование строки в объект Date
                    formattedDate = serverFormat.format(date);
                    // Отправьте formattedDate на сервер
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Обработка ошибки парсинга, если введенная дата имеет некорректный формат
                }
                Log.d("API", "Sending goal: " +
                        "UserId: " + LoginFunctions.getLoggedUser().getIdUser() +
                        ", Date: " + formattedDate +
                        ", Name: " + editTextGoalName.getText().toString() +
                        ", Amount: " + Integer.parseInt(editTextGoalAmount.getText().toString()));

                Call<Goal> postGoal = apiInterface.postGoal(new Goal(
                        0,
                        LoginFunctions.getLoggedUser().getIdUser(),
                        null,
                        null,
                        formattedDate,
                        editTextGoalName.getText().toString(),
                        Integer.parseInt(editTextGoalAmount.getText().toString()),
                        0,
                        false
                ));
                postGoal.enqueue(new Callback<Goal>() {
                    @Override
                    public void onResponse(Call<Goal> call, Response<Goal> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Цель добавлена", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getApplicationContext(), "Ошибка добавления цели", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Goal> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("API", t.getMessage());
                    }
                });
            }
        });


        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Обработка выбранной даты
                        String selectedDate = selectedDayOfMonth + "." + (selectedMonth + 1) + "." + selectedYear;

                        // Установите выбранную дату в EditText
                        editTextGoalDate.setText(selectedDate);


                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}