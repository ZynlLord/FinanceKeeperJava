package com.example.financekeeperjava.presentation.main.operations;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.ApiFunc;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.func.WalletFunc;
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.data.model.Wallet;
import com.example.financekeeperjava.presentation.main.MenuActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOperationActivity extends AppCompatActivity {

    private EditText sumEditText;
    private String currentInput = "";
    private double result = 0;
    private String operator = "";
    private Spinner spinner_wallet, spinner_type, category_spinner;
    private EditText comment_et;

    private TextView selected_type_tv, selected_wallet_tv, selected_category_tv;
    private final String[] typeArray = {"Доход", "Расход", "Перевод"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operation);
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        spinner_wallet = findViewById(R.id.wallet_spinner);
        spinner_type = findViewById(R.id.type_spinner);
        comment_et = findViewById(R.id.comment_et);
        category_spinner = findViewById(R.id.category_spinner);
        selected_category_tv = findViewById(R.id.selected_category_tv);
        selected_type_tv = findViewById(R.id.selected_type_tv);
        selected_wallet_tv = findViewById(R.id.selected_wallet_tv);
        AppCompatButton add_operation_btn = findViewById(R.id.add_Operation);

        add_operation_btn.setOnClickListener(new View.OnClickListener() { // Метод для обработки нажатия на кнопку "Добавить"
            @Override
            public void onClick(View v) {
                int idUser = LoginFunctions.getLoggedUser().getIdUser();
                int amount = Integer.parseInt(sumEditText.getText().toString()); // возможна ошибка, задать et input type number

                String comment = comment_et.getText().toString();
                String type = selected_type_tv.getText().toString();

                String walletName = selected_wallet_tv.getText().toString();
                String categoryName = selected_category_tv.getText().toString();

                ApiFunc.getWalletByNameAndIdUser(idUser, walletName, new ApiFunc.WalletCallBack() {
                    @Override
                    public void onWalletLoaded(Wallet wallet) {
                        int walletID = wallet.getIdWallet();

                        // Получение categoryId
                        ApiFunc.getCategoryIDByName(categoryName, new ApiFunc.CategoryCallBack() {
                            @Override
                            public void onCategoryLoaded(Category category) {
                                int categoryId = category.getIdCategory();
                                // Добавление операции с использованием walletId и categoryId
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    addOperation(idUser, walletID, categoryId, amount, comment, type);
                                }
                            }

                            @Override
                            public void onError(String errorMessage) {
                                Log.e("API", "Failed to found category id");
                            }
                        });
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", "Failed to found wallet id");
                    }
                });
            }
        });


        Call<ArrayList<Wallet>> getWalletsList = apiInterface.getWalletList();
        getWalletsList.enqueue(new Callback<ArrayList<Wallet>>() {
            @Override
            public void onResponse(Call<ArrayList<Wallet>> call, Response<ArrayList<Wallet>> response) {
                if (response.isSuccessful()) {
                    int idUser = LoginFunctions.getLoggedUser().getIdUser();
                    ArrayList<Wallet> allWallets = response.body();
                    ArrayList<Wallet> userWallets = new ArrayList<>();
                    ArrayList<String> walletNames = new ArrayList<>();
                    for (Wallet w : allWallets) {
                        if (w.getIdUser() == idUser) {
                            userWallets.add(w);
                        }
                    }
                    for (Wallet w : userWallets) {
                        walletNames.add(w.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, walletNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_wallet.setAdapter(adapter);
                    spinner_wallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedWallet = spinner_wallet.getSelectedItem().toString();
                            selected_wallet_tv.setText(selectedWallet);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Toast.makeText(getApplicationContext(), "Кошелёк не выбран", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(getApplicationContext(), "Ошибка загрузки кошельков", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Wallet>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, typeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = spinner_type.getSelectedItem().toString();
                selected_type_tv.setText(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Тип не выбран", Toast.LENGTH_SHORT).show();
            }
        });


        Call<ArrayList<Category>> getCategories = apiInterface.getCategoriesList();

        getCategories.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = response.body();
                    ArrayList<String> categoryNames = new ArrayList<>();
                    for (Category c : categories) {
                        categoryNames.add(c.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categoryNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category_spinner.setAdapter(adapter);
                    category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedCategory = category_spinner.getSelectedItem().toString();
                            selected_category_tv.setText(selectedCategory);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Toast.makeText(getApplicationContext(), "Категория не выбрана", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else
                    Toast.makeText(getApplicationContext(), "Ошбика загрузки категорий", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });

        sumEditText = findViewById(R.id.sum_et);

        Button[] numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            int buttonId = getResources().getIdentifier(getString(R.string.button) + i, getString(R.string.id), getPackageName());
            numberButtons[i] = findViewById(buttonId);
            final int finalI = i;
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentInput += String.valueOf(finalI);
                    sumEditText.setText(currentInput);
                }
            });
        }

        Button buttonDot = findViewById(R.id.buttonDot);
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    sumEditText.setText(currentInput);
                }
            }
        });

        Button buttonClearOne = findViewById(R.id.buttonClearOne);
        buttonClearOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    sumEditText.setText(currentInput);
                }
            }
        });

        Button buttonClearAll = findViewById(R.id.buttonClearAll);
        buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                result = 0;
                sumEditText.setText("");
            }
        });

        Button[] operatorButtons = new Button[4];
        operatorButtons[0] = findViewById(R.id.buttonPlus);
        operatorButtons[1] = findViewById(R.id.buttonMinus);
        operatorButtons[2] = findViewById(R.id.buttonMultiply);
        operatorButtons[3] = findViewById(R.id.buttonDivide);

        for (int i = 0; i < 4; i++) {
            final int finalI = i;
            operatorButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!currentInput.isEmpty()) {
                        result = Double.parseDouble(currentInput);
                        currentInput = "";
                        operator = getOperator(finalI);
                    }
                }
            });
        }

        Button buttonEquals = findViewById(R.id.buttonEquals);
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty() && !operator.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    switch (operator) {
                        case "+":
                            result += secondOperand;
                            break;
                        case "-":
                            result -= secondOperand;
                            break;
                        case "*":
                            result *= secondOperand;
                            break;
                        case "/":
                            if (secondOperand != 0) {
                                result /= secondOperand;
                            } else {
                                // Handle division by zero error
                            }
                            break;
                    }
                    sumEditText.setText(String.valueOf(result));
                    currentInput = "";
                    operator = "";
                }
            }
        });

        Button buttonPercent = findViewById(R.id.buttonPercent);
        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty() && operator.isEmpty()) {
                    double inputNumber = Double.parseDouble(currentInput);
                    double percent = Double.parseDouble(currentInput) / 100; // Получаем процент от числа
                    sumEditText.setText(String.valueOf(percent));
                    currentInput = String.valueOf(percent);
                }
            }
        });

        Button buttonTripleZero = findViewById(R.id.button000);
        buttonTripleZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput += "000";
                sumEditText.setText(currentInput);
            }
        });

    }

    private String getOperator(int index) {
        switch (index) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            default:
                return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addOperation(int idUser, int walletId, int categoryId, int amount, String comment, String type) {
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        // Получение текущей даты и времени
        LocalDateTime currentDateTime = LocalDateTime.now();

// Уменьшение текущей даты и времени на одну минуту
        LocalDateTime modifiedDateTime = currentDateTime.minusDays(1);

// Определение формата
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

// Преобразование в строку с заданным форматом
        String formattedDateTime = modifiedDateTime.format(formatter);
        if (type.equals("Доход")) {
            Call<Operation> postOperation = apiInterface.postOperation(new Operation(
                    formattedDateTime,
                    idUser,
                    formattedDateTime,
                    amount,
                    null,
                    comment,
                    type,
                    walletId,
                    categoryId
            ));
            Log.d("API", "Добавление операции: idUser=" + idUser + ", walletId=" + walletId + ", categoryId=" + categoryId + ", amount=" + amount + ", comment=" + comment + ", type=" + type + "date: " + formattedDateTime);
            postOperation.enqueue(new Callback<Operation>() {
                @Override
                public void onResponse(Call<Operation> call, Response<Operation> response) {
                    if (response.isSuccessful()) {
                        Wallet oldWallet = WalletFunc.getActiveWallet();
                        Wallet updWallet = oldWallet;
                        int oldBalance = oldWallet.getBalance();
                        int newBalance = oldBalance + amount;
                        updWallet.setBalance(newBalance);
                        WalletFunc.setNoActiveWallet();
                        WalletFunc.saveWallet(updWallet);
                        Toast.makeText(getApplicationContext(), "Операция успешно добавлена!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("API", "Ошибка в onResponse: " + response.message());
                        Toast.makeText(getApplicationContext(), "Ошибка добавления операции", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Operation> call, Throwable t) {
                    Log.e("API", "Не удалось добавить операцию: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (type.equals("Расход")) {
            Call<Operation> postOperation = apiInterface.postOperation(new Operation(
                    formattedDateTime,
                    idUser,
                    formattedDateTime,
                    amount,
                    null,
                    comment,
                    type,
                    walletId,
                    categoryId
            ));
            Log.d("API", "Добавление операции: idUser=" + idUser + ", walletId=" + walletId + ", categoryId=" + categoryId + ", amount=" + amount + ", comment=" + comment + ", type=" + type + "date: " + formattedDateTime);
            postOperation.enqueue(new Callback<Operation>() {
                @Override
                public void onResponse(Call<Operation> call, Response<Operation> response) {
                    if (response.isSuccessful()) {
                        Wallet oldWallet = WalletFunc.getActiveWallet();
                        Wallet updWallet = oldWallet;
                        int oldBalance = oldWallet.getBalance();
                        int newBalance = oldBalance - amount;
                        updWallet.setBalance(newBalance);
                        WalletFunc.setNoActiveWallet();
                        WalletFunc.saveWallet(updWallet);
                        Toast.makeText(getApplicationContext(), "Операция успешно добавлена!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("API", "Ошибка в onResponse: " + response.message());
                        Toast.makeText(getApplicationContext(), "Ошибка добавления операции", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Operation> call, Throwable t) {
                    Log.e("API", "Не удалось добавить операцию: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }



    }
}

