package com.example.financekeeperjava.presentation.main.operations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.adapters.NotesAdapter;
import com.example.financekeeperjava.adapters.OperationsAdapter;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.presentation.main.stats.PreStatsActivity;
import com.example.financekeeperjava.presentation.main.stats.StatsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OperationsFragment extends Fragment {

    Button category_add_btn;
    ApiInterface apiInterface;

    RecyclerView operationsView;

    OperationsAdapter adapter;

    Context mContext;

    FloatingActionButton add_operation_fab, fab_statistic;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*category_add_btn = view.findViewById(R.id.category_add_btn);*/
        operationsView = view.findViewById(R.id.operations_recycler);
        apiInterface = API.buildRequest().create(ApiInterface.class);
        add_operation_fab = view.findViewById(R.id.fab_add_operation);
        fab_statistic = view.findViewById(R.id.fab_statistic);

        fab_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PreStatsActivity.class);
                startActivity(intent);
            }
        });

        adapter = new OperationsAdapter(mContext, new ArrayList<>());
        operationsView.setLayoutManager(new LinearLayoutManager(mContext));
        operationsView.setHasFixedSize(true);
        operationsView.setAdapter(adapter);

        Call<ArrayList<Operation>> getOperationsCall = apiInterface.getOperationsList();

        getOperationsCall.enqueue(new Callback<ArrayList<Operation>>() {
            @Override
            public void onResponse(Call<ArrayList<Operation>> call, Response<ArrayList<Operation>> response) {
                ArrayList<Operation> myOperations = new ArrayList<>();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        for (Operation op :
                                response.body()) {
                            if (op.getIdUser() == LoginFunctions.getLoggedUser().getIdUser()) {
                                myOperations.add(op);
                                adapter.updateData(myOperations);
                            }
                        }
                    }
                } else {
                    Toast.makeText(mContext, "Ошибка со стороны клиента", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Operation>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });

        add_operation_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOperationActivity.class);
                startActivity(intent);
            }
        });

    }
}