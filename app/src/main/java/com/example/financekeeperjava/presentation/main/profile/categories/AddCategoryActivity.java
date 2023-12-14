package com.example.financekeeperjava.presentation.main.profile.categories;

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
import com.example.financekeeperjava.data.model.Category;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {

    EditText category_icon_et, category_name_et;
    Button add_category_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        category_icon_et = findViewById(R.id.category_icon_et);
        category_name_et = findViewById(R.id.category_name_et);
        add_category_btn = findViewById(R.id.add_category_btn);

        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);


        add_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Category> postCategory = apiInterface.postCategory(new Category(
                        null,
                        category_name_et.getText().toString(),
                        category_icon_et.getText().toString(),
                        null
                ));
                postCategory.enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call, Response<Category> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(getApplicationContext(), "Ошибка со стороны клиента", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("API", t.getMessage());
                    }
                });
            }
        });


    }
}