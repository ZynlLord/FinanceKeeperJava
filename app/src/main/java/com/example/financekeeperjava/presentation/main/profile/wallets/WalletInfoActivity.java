package com.example.financekeeperjava.presentation.main.profile.wallets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.func.WalletFunc;
import com.example.financekeeperjava.data.model.Wallet;

public class WalletInfoActivity extends AppCompatActivity {

    TextView wallet_name, wallet_balance, wallet_currency;
    Button setActive_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);
        wallet_balance = findViewById(R.id.wallet_balance_tv);
        wallet_currency = findViewById(R.id.wallet_currency_tv);
        wallet_name = findViewById(R.id.wallet_name_tv);
        setActive_Button = findViewById(R.id.setActive_Button);

        String walletName = getIntent().getStringExtra("walletName");
        String walletCurrency = getIntent().getStringExtra("walletCurrency");
        int walletBalance = getIntent().getIntExtra("walletBalance", 0);
        int walletUserId = getIntent().getIntExtra("walletUserId", 0);
        String walletCreatedAt = getIntent().getStringExtra("walletCreatedAt");
        String walletIcon = getIntent().getStringExtra("walletIcon");
        int walletId = getIntent().getIntExtra("walletId", 0);

        wallet_name.setText(walletName);
        wallet_balance.setText(String.valueOf(walletBalance));
        wallet_currency.setText(walletCurrency);

        setActive_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WalletFunc.isActiveWalletExists()) {
                    String currentWalletName = WalletFunc.getActiveWallet().getName();
                    Toast.makeText(getApplicationContext(), "Кошелёк" + currentWalletName + " больше не активный", Toast.LENGTH_SHORT).show();
                    WalletFunc.setNoActiveWallet();
                    WalletFunc.saveWallet(new Wallet(
                            walletUserId,
                            walletCreatedAt,
                            walletBalance,
                            walletName,
                            walletIcon,
                            walletCurrency,
                            walletId
                    ));
                } else
                    WalletFunc.saveWallet(new Wallet(
                            walletUserId,
                            walletCreatedAt,
                            walletBalance,
                            walletName,
                            walletIcon,
                            walletCurrency,
                            walletId
                    ));
                Toast.makeText(getApplicationContext(), "Данный кошелёк выбран как активный", Toast.LENGTH_SHORT).show();
            }
        });
    }
}