package com.example.financekeeperjava.presentation.main.profile.wallets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Wallet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWalletActivity extends AppCompatActivity {
    EditText editTextWalletName, editTextWalletBalance, editTextWalletCurrency;

    Button buttonSaveWallet;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        apiInterface = API.buildRequest().create(ApiInterface.class);

        editTextWalletBalance = findViewById(R.id.editTextWalletBalance);
        editTextWalletCurrency = findViewById(R.id.editTextWalletCurrency);
        editTextWalletName = findViewById(R.id.editTextWalletName);
        buttonSaveWallet = findViewById(R.id.buttonSaveWallet);

        buttonSaveWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Обработка нажатия на кнопку добавления кошелька
                Call<Wallet> postWallet = apiInterface.postWallet(new Wallet(
                        LoginFunctions.getLoggedUser().getIdUser(),
                        null,
                        Integer.parseInt(editTextWalletBalance.getText().toString()),
                        editTextWalletName.getText().toString(),
                        "none",
                        editTextWalletCurrency.getText().toString(),
                        null
                ));

                postWallet.enqueue(new Callback<Wallet>() { // POST-запрос для добавления кошелька
                    @Override
                    public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Кошелёк успешно создан", Toast.LENGTH_SHORT).show();
                            updateWalletList();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка добавления кошелька", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Wallet> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("API", t.getMessage());
                    }
                });
            }
        });
    }

    // Метод для обновления списка кошельков
    private void updateWalletList() {
        Call<ArrayList<Wallet>> getWallets = apiInterface.getWalletList();

        getWallets.enqueue(new Callback<ArrayList<Wallet>>() {
            @Override
            public void onResponse(Call<ArrayList<Wallet>> call, Response<ArrayList<Wallet>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Wallet> allWallets = response.body();
                    ArrayList<Wallet> userWallets = new ArrayList<>();
                    for (Wallet w : allWallets) {
                        if (w.getIdUser() == LoginFunctions.getLoggedUser().getIdUser()) {
                            userWallets.add(w);
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), WalletActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Очистить стек активностей
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка загрузки кошельков", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Wallet>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });
    }
}
