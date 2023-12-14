package com.example.financekeeperjava.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.presentation.main.MenuActivity;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button sign_up_btn, sign_in_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(getApplicationContext());
        if (!LoginFunctions.isLogin()) {

            sign_in_btn = findViewById(R.id.sign_in_btn);
            sign_up_btn = findViewById(R.id.sign_up_btn);

            sign_up_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                }
            });

            sign_in_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        }

    }
}