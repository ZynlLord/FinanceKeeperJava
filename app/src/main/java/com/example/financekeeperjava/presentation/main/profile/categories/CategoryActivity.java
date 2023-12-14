package com.example.financekeeperjava.presentation.main.profile.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.adapters.CategoryAdapter;
import com.example.financekeeperjava.adapters.OperationsAdapter;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.presentation.main.goals.AddGoalActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView category_recycler;
    CategoryAdapter adapter;

    FloatingActionButton fab_add_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        category_recycler = findViewById(R.id.category_recycler);
        fab_add_category = findViewById(R.id.fab_add_category);
        adapter = new CategoryAdapter(getApplicationContext(), new ArrayList<>());
        category_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        category_recycler.setHasFixedSize(true);
        category_recycler.setAdapter(adapter);

        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        Call<ArrayList<Category>> getCategories = apiInterface.getCategoriesList();

        fab_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        getCategories.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = response.body();
                    adapter.updateData(categories);
                }
                else
                    Toast.makeText(getApplicationContext(), "Ошибка со стороны клиента", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });
    }
}