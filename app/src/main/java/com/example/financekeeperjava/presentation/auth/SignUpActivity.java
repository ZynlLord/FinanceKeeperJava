package com.example.financekeeperjava.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button sign_up_btn;
    TextView to_login_tv;

    EditText surname_et, name_et, login_et, password_et, repeat_et, avatar_et;
    TextView surname_err_tv, name_err_tv, repeat_err_tv, login_err_tv, password_error_tv;

    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiInterface = API.buildRequest().create(ApiInterface.class);

        sign_up_btn = findViewById(R.id.sign_up_btn);
        to_login_tv = findViewById(R.id.to_login_tv);
        surname_et = findViewById(R.id.surname_et);
        name_et = findViewById(R.id.name_et);
        login_et = findViewById(R.id.login_et);
        password_et = findViewById(R.id.password_et);
        repeat_et = findViewById(R.id.password_repeat_et);
        surname_err_tv = findViewById(R.id.surname_err_tv);
        name_err_tv = findViewById(R.id.name_err_tv);
        repeat_err_tv = findViewById(R.id.repeat_err_tv);
        login_err_tv = findViewById(R.id.login_err_tv);
        password_error_tv = findViewById(R.id.password_error_tv);
        avatar_et = findViewById(R.id.avatar_et);


        to_login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String surname = surname_et.getText().toString().trim();
                String name = name_et.getText().toString().trim();
                String login = login_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();
                String repeat = repeat_et.getText().toString().trim();
                String avatar = avatar_et.getText().toString();

                if (LoginFunctions.validateLoginAndPassword(login, password)) {
                    if (password.equals(repeat)) {
                        Call<User> userCall = apiInterface.postUser(new User(
                                null,
                                null,
                                password,
                                "Пользователь",
                                surname,
                                name,
                                avatar,
                                login
                        ));
                        userCall.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                             //       Toast.makeText(getApplicationContext(), "Успешная регистрация!", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(getApplicationContext(), "Такие логин и пароль уже есть в системе", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("API", t.getMessage());
                            }
                        });
                    } else {
                       // Toast.makeText(getApplicationContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                        repeat_err_tv.setVisibility(View.VISIBLE);
                    }
                } else {
                    // surname_et, name_et, login_et, password_et, repeat_et
                   // Toast.makeText(getApplicationContext(), "Данные не прошли валидацию", Toast.LENGTH_LONG).show();
                    if (surname_et.getText().toString().equals(""))
                        surname_err_tv.setVisibility(View.VISIBLE);
                    else surname_err_tv.setVisibility(View.GONE);
                    if (name_et.getText().toString().equals(""))
                        name_err_tv.setVisibility(View.VISIBLE);
                    else name_err_tv.setVisibility(View.GONE);
                    if (!LoginFunctions.validateLogin(login))
                        login_err_tv.setVisibility(View.VISIBLE);
                    else login_err_tv.setVisibility(View.GONE);
                    if (!LoginFunctions.validatePassword(password))
                        password_error_tv.setVisibility(View.VISIBLE);
                    else password_error_tv.setVisibility(View.GONE);

                }
            }
        });
    }
}