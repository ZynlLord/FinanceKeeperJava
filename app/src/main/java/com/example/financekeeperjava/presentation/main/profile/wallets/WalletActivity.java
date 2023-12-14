package com.example.financekeeperjava.presentation.main.profile.wallets;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.adapters.WalletListAdapter;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Wallet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {

    Button add_wallet_btn;
    ApiInterface apiInterface;

    ListView wallet_listView;
    WalletListAdapter walletListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        add_wallet_btn = findViewById(R.id.add_wallet_btn);
        apiInterface = API.buildRequest().create(ApiInterface.class);
        wallet_listView = findViewById(R.id.wallet_list_view);

        add_wallet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddWalletActivity.class);
                startActivity(intent);
            }
        });

        // Инициализация адаптера и загрузка списка кошельков
        walletListAdapter = new WalletListAdapter(getApplicationContext(), new ArrayList<Wallet>());
        wallet_listView.setAdapter(walletListAdapter);

        loadWallets();
    }

    // Метод для загрузки списка кошельков
    private void loadWallets() {
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

                    // Обновляем данные в адаптере и уведомляем его о изменениях
                    walletListAdapter.clear();
                    walletListAdapter.addAll(userWallets);
                    walletListAdapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        super.onResume();
        // При возвращении к активности из AddWalletActivity, обновляем список кошельков
        loadWallets();
    }
}
