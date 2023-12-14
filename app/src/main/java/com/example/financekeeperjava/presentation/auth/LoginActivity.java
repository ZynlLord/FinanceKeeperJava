package com.example.financekeeperjava.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.User;
import com.example.financekeeperjava.presentation.main.MenuActivity;
import com.example.financekeeperjava.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    Button login_btn;
    TextView to_sign_up_tv;

    EditText login_et, password_et;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn = findViewById(R.id.login_btn);
        to_sign_up_tv = findViewById(R.id.to_sign_up_tv);
        apiInterface = API.buildRequest().create(ApiInterface.class);

        login_et = findViewById(R.id.login_et);
        password_et = findViewById(R.id.password_et);


        to_sign_up_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = login_et.getText().toString();
                String password = password_et.getText().toString();
                Call<ArrayList<User>> getUserList = apiInterface.getUserList();
                getUserList.enqueue(new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        if (response.isSuccessful()) { // Обработка действий при успешном запросе
                            ArrayList<User> users = response.body();
                            User user = LoginFunctions.findUser(login, password, users); // Вызов метода поиска пользователя
                            if (user == null) {
                                Toast.makeText(LoginActivity.this, "Такого пользователя не существует", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            LoginFunctions.saveUser(user);
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class); // Переход на новую активность
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка со стороны клиента", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}