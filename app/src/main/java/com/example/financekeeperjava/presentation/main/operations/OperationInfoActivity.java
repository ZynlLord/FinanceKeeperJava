package com.example.financekeeperjava.presentation.main.operations;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.ApiFunc;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.data.model.Wallet;
import com.example.financekeeperjava.presentation.main.MenuActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationInfoActivity extends AppCompatActivity {

    TextView category_tv, sum_tv, date_tv, wallet_name_tv;
    ConstraintLayout delete_lay;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_info);
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        category_tv = findViewById(R.id.category_tv);
        sum_tv = findViewById(R.id.sum_tv);
        date_tv = findViewById(R.id.date_tv);
        wallet_name_tv = findViewById(R.id.wallet_name_tv);
        delete_lay = findViewById(R.id.delete_lay);
        category_tv.setText(getIntent().getStringExtra("categoryName"));
        int sum = getIntent().getIntExtra("sumOperation", 0);
        sum_tv.setText(String.valueOf(sum));
        int idOperation = getIntent().getIntExtra("idOperation", 0);

        String inputDate = getIntent().getStringExtra("dateOperation");
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]");
        LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));
        String outputDate = dateTime.format(outputFormatter);
        date_tv.setText(outputDate);


        ApiFunc.getOperationById(idOperation, new ApiFunc.OperationCallback() {
            @Override
            public void onOperationLoaded(Operation operation) {
                int walletId = operation.getIdWallet();
                ApiFunc.getWalletByID(walletId, new ApiFunc.WalletCallBack() {
                    @Override
                    public void onWalletLoaded(Wallet wallet) {
                        wallet_name_tv.setText(wallet.getName());
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", "Failed to found wallet id");
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API", "Failed to found operation id");
            }
        });


        delete_lay.setOnClickListener(new View.OnClickListener() { // Обработка нажатия на кнопку "Удалить операцию"
            @Override
            public void onClick(View v) {
                Call<Void> deleteOperation = apiInterface.deleteOperation(idOperation);

                deleteOperation.enqueue(new Callback<Void>() { //Метод обработки запроса на удаление операции
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Операция успешно удалена", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getApplicationContext(), "Ошибка удаления операции", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}