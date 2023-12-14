package com.example.financekeeperjava.presentation.main.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.func.WalletFunc;
import com.example.financekeeperjava.data.model.User;
import com.example.financekeeperjava.presentation.auth.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountEditActivity extends AppCompatActivity {


    private TextView firstNameLabel, lastNameLabel, loginLabel, passwordLabel;
    private EditText firstNameEditText, lastNameEditText, loginEditText, passwordEditText;
    private Button editButton, saveButton, leaveButton;

    private ImageView avatarView;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        apiInterface = API.buildRequest().create(ApiInterface.class);

        firstNameLabel = findViewById(R.id.firstname_label);
        lastNameLabel = findViewById(R.id.secondname_label);
        loginLabel = findViewById(R.id.login_label);
        passwordLabel = findViewById(R.id.password_label);
        firstNameEditText = findViewById(R.id.firstname_et);
        lastNameEditText = findViewById(R.id.secondname_et);
        loginEditText = findViewById(R.id.login_et);
        passwordEditText = findViewById(R.id.password_et);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);
        avatarView = findViewById(R.id.avatarImageView);
        leaveButton = findViewById(R.id.leave_button);

        setFieldsEditable(false);

        loadAllData();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFieldsEditable(true);
                editButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

                String formattedDate = dateFormat.format(currentDate);

                int idUser = LoginFunctions.getLoggedUser().getIdUser();
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String firstname = firstNameEditText.getText().toString();
                String lastname = lastNameEditText.getText().toString();
                if (LoginFunctions.validateLoginAndPassword(login, password)) {
                    Call<User> updateCall = apiInterface.updateUser(idUser, new User(
                            idUser,
                            formattedDate,
                            password,
                            "Пользователь",
                            firstname,
                            lastname,
                            "none",
                            login
                    ));
                    updateCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                setFieldsEditable(false);
                                editButton.setVisibility(View.VISIBLE);
                                saveButton.setVisibility(View.GONE);
                            } else {
                                int statusCode = response.code(); // Получение кода состояния HTTP
                                String errorMessage = response.message(); // Получение текста ошибки от сервера (если есть)
                                // Дополнительная обработка и вывод информации об ошибке
                                Log.e("API", "HTTP Status Code: " + statusCode);
                                Log.e("API", "Error Message: " + errorMessage);
                                Toast.makeText(getApplicationContext(), "Такие логин и пароль уже есть в системе", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("API", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Данные не прошли валидацию", Toast.LENGTH_LONG).show();
                }
            }
        });

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFunctions.logout();
                WalletFunc.setNoActiveWallet();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    private void setFieldsEditable(boolean editable) {
        // Включить или отключить редактирование полей
        firstNameEditText.setEnabled(editable);
        lastNameEditText.setEnabled(editable);
        loginEditText.setEnabled(editable);
        passwordEditText.setEnabled(editable);
    }

    private void loadAllData() {
        int idUser = LoginFunctions.getLoggedUser().getIdUser();
        Call<ArrayList<User>> getUserList = apiInterface.getUserList();
        getUserList.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    User user = LoginFunctions.findUserByID(idUser, response.body());
                    if (user != null) {
                        firstNameEditText.setText(user.getName());
                        lastNameEditText.setText(user.getSurname());
                        loginEditText.setText(user.getLogin());
                        passwordEditText.setText(user.getPassword());
                    } else {
                        Toast.makeText(getApplicationContext(), "Ошибка загрузки данных", Toast.LENGTH_LONG).show();
                    }

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
}