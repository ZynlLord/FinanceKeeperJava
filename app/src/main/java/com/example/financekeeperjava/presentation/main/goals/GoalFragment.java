package com.example.financekeeperjava.presentation.main.goals;

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
import android.widget.Toast;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.adapters.GoalsAdapter;
import com.example.financekeeperjava.adapters.OperationsAdapter;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.Goal;
import com.example.financekeeperjava.data.model.Operation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalFragment extends Fragment {


    RecyclerView recyclerViewGoals;

    GoalsAdapter goalsAdapter;
    FloatingActionButton fab_addGoal;

    Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewGoals = view.findViewById(R.id.recyclerViewGoals);
        fab_addGoal = view.findViewById(R.id.fabAddGoal);

        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);


        goalsAdapter = new GoalsAdapter(mContext, new ArrayList<>());
        recyclerViewGoals.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewGoals.setHasFixedSize(true);
        recyclerViewGoals.setAdapter(goalsAdapter);

        Call<ArrayList<Goal>> getGoals = apiInterface.getAllGoals();

        getGoals.enqueue(new Callback<ArrayList<Goal>>() {
            @Override
            public void onResponse(Call<ArrayList<Goal>> call, Response<ArrayList<Goal>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Goal> goaals = new ArrayList<>();
                    assert response.body() != null;
                    for (Goal goal : response.body()) {
                        if (goal.getIdUser() == LoginFunctions.getLoggedUser().getIdUser()) {
                            goaals.add(goal);
                        }
                    }
                    goalsAdapter.updateData(goaals);
                }
                else
                    Toast.makeText(mContext, "Ошибка со стороны клиента", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Goal>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("API", t.getMessage());
            }
        });



        fab_addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddGoalActivity.class);
                startActivity(intent);
            }
        });
    }
}