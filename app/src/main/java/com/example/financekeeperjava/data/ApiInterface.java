package com.example.financekeeperjava.data;

import com.example.financekeeperjava.data.model.Budget;
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.ExpenseTemplate;
import com.example.financekeeperjava.data.model.Goal;
import com.example.financekeeperjava.data.model.IncomeSource;
import com.example.financekeeperjava.data.model.Log;
import com.example.financekeeperjava.data.model.Note;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.data.model.Transaction;
import com.example.financekeeperjava.data.model.User;
import com.example.financekeeperjava.data.model.Wallet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("notes")
    Call<ArrayList<Note>> getNotesList();

    @POST("notes")
    Call<Note> postNote(@Body Note note);

    @DELETE("notes/{id}")
    Call<Void> deleteNote(@Path("id") int noteId);

    @PUT("notes/{id}")
    Call<Note> updateNote(@Path("id") int noteId, @Body Note updatedNote);

    @POST("users")
    Call<User> postUser(@Body User user);

    @GET("users")
    Call<ArrayList<User>> getUserList();

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") int userId, @Body User updatedUser);

    @GET("categories")
    Call<ArrayList<Category>> getCategoriesList();

    @POST("categories")
    Call<Category> postCategory(@Body Category category);

    @PUT("categories/{id}")
    Call<Category> updateCategory(@Path("id") int id, @Body Category category);

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(@Path("id") int id);

    @GET("operations")
    Call<ArrayList<Operation>> getOperationsList();

    @POST("operations")
    Call<Operation> postOperation(@Body Operation operation);

    @PUT("operations/{id}")
    Call<Operation> updateOperation(@Path("id") int id, @Body Operation operation);

    @DELETE("operations/{id}")
    Call<Void> deleteOperation(@Path("id") int id);

    @GET("wallets")
    Call<ArrayList<Wallet>> getWalletList();

    @POST("wallets")
    Call<Wallet> postWallet(@Body Wallet wallet);

    @PUT("wallets/{id}")
    Call<Wallet> updateWallet(@Path("id") int id, @Body Wallet updatedWallet);

    @DELETE("wallets/{id}")
    Call<Void> deleteWallet(@Path("id") int id);

    @GET("incomesources")
    Call<ArrayList<IncomeSource>> getIncomeSources();

    @POST("incomesources")
    Call<IncomeSource> postIncomeSource(@Body IncomeSource incomeSource);

    @PUT("incomesources/{id}")
    Call<IncomeSource> updateIncomeSource(@Path("id") int id, @Body IncomeSource incomeSource);

    @DELETE("incomesources/{id}")
    Call<Void> deleteIncomeSource(@Path("id") int id);

    @GET("expensetemplates")
    Call<ArrayList<ExpenseTemplate>> getExpenseTemplates();

    @POST("expensetemplates")
    Call<ExpenseTemplate> postExpenseTemplate(@Body ExpenseTemplate expenseTemplate);

    @PUT("expensetemplates/{id}")
    Call<ExpenseTemplate> updateExpenseTemplate(@Path("id") int id, @Body ExpenseTemplate updatedExpenseTemplate);

    @DELETE("expensetemplates/{id}")
    Call<Void> deleteExpenseTemplate(@Path("id") int id);

    @POST("logs")
    Call<Log> postLog(@Body Log log);

    @GET("budgets")
    Call<ArrayList<Budget>> getBudgets();

    @POST("budgets")
    Call<Budget> postBudget(@Body Budget budget);

    @PUT("budgets/{id}")
    Call<Budget> updateBudget(@Path("id") int id, @Body Budget updatedBudget);

    @DELETE("budgets/{id}")
    Call<Void> deleteBudget(@Path("id") int id);

    @GET("transactions")
    Call<ArrayList<Transaction>> getTransactions();

    @POST("transactions")
    Call<Transaction> postTransaction(@Body Transaction transaction);

    @DELETE("transactions/{id}")
    Call<Void> deleteTransaction(@Path("id") int id);

    @PUT("transactions/{id}")
    Call<Transaction> updateTransaction(@Path("id") int id, @Body Transaction updatedTransaction);

    @GET("goals")
    Call<ArrayList<Goal>> getAllGoals();

    @POST("goals")
    Call<Goal> postGoal(@Body Goal goal);

    @PUT("goals/{id}")
    Call<Goal> updateGoal(@Path("id") int id, @Body Goal updatedGoal);

    @DELETE("goals/{id}")
    Call<Void> deleteGoal(@Path("id") int id);




}
