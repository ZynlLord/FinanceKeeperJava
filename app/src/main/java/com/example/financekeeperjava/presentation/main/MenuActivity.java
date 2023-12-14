package com.example.financekeeperjava.presentation.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.func.WalletFunc;
import com.example.financekeeperjava.data.model.Log;
import com.example.financekeeperjava.data.model.Wallet;
import com.example.financekeeperjava.presentation.main.goals.GoalFragment;
import com.example.financekeeperjava.presentation.main.notes.NotesFragment;
import com.example.financekeeperjava.presentation.main.operations.OperationsFragment;
import com.example.financekeeperjava.presentation.main.profile.ProfileFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView walletBalanceTextView;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setFragment(new NotesFragment());
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
        walletBalanceTextView = findViewById(R.id.walletBalanceTextView);
        Wallet wallet = WalletFunc.getActiveWallet();
        if (wallet != null) {
            walletBalanceTextView.setText(String.format("Кошелёк: %s Баланс: %d", wallet.getName(), wallet.getBalance()));
        } else {
             walletBalanceTextView.setText("Нет активного кошелька!");
        }

        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.first_item) {
                    setFragment(new NotesFragment());
                    return true;
                } else if (item.getItemId() == R.id.second_item) {
                    setFragment(new OperationsFragment());
                    return true;
                } else if (item.getItemId() == R.id.third_item) {
                    setFragment(new ProfileFragment());
                    return true;
                } else if (item.getItemId() == R.id.fourth_item) {
                    setFragment(new GoalFragment());
                    return true;
                }
                return true;
            }
        });
    }


    void setFragment(Fragment fragment) { //Метод установки фрагмента
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, null)
                .commit();
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onResume() {
        super.onResume();
        Wallet wallet = WalletFunc.getActiveWallet();
        if (wallet != null) {
            walletBalanceTextView.setText(String.format("Кошелёк: %s Баланс: %d", wallet.getName(), wallet.getBalance()));
        } else {
            walletBalanceTextView.setText("Нет активного кошелька!");
        }
    }
}