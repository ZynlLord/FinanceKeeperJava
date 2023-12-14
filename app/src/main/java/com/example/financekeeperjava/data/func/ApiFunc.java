package com.example.financekeeperjava.data.func;

import android.widget.Toast;

import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.Operation;
import com.example.financekeeperjava.data.model.Wallet;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiFunc {
    public interface OperationCallback {
        void onOperationLoaded(Operation operation);

        void onError(String errorMessage);
    }

    public interface CategoryCallBack {
        void onCategoryLoaded(Category category);

        void onError(String errorMessage);
    }

    public interface WalletCallBack {
        void onWalletLoaded(Wallet wallet);

        void onError(String errorMessage);
    }


    public static void getOperationById(int id, final OperationCallback callback) {
        ApiInterface apiInterface;
        apiInterface = API.buildRequest().create(ApiInterface.class);
        Call<ArrayList<Operation>> getOperationsList = apiInterface.getOperationsList();
        getOperationsList.enqueue(new Callback<ArrayList<Operation>>() {
            @Override
            public void onResponse(Call<ArrayList<Operation>> call, Response<ArrayList<Operation>> response) {
                if (response.isSuccessful()) {
                    for (Operation op : response.body()) {
                        if (op.getIdOperation() == id) {
                            // Возвращаем операцию через колбэк
                            callback.onOperationLoaded(op);
                            return;
                        }
                    }
                    // Если операция с заданным id не найдена, вызываем onError
                    callback.onError("Операция не найдена");
                } else {
                    // Обработка ошибки при выполнении запроса
                    callback.onError("Ошибка при выполнении запроса");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Operation>> call, Throwable t) {
                // Обработка ошибки при выполнении запроса
                callback.onError(t.getMessage());
            }
        });
    }

    public static void getCategoryByID(int id, final CategoryCallBack callback) {
        ApiInterface apiInterface;
        apiInterface = API.buildRequest().create(ApiInterface.class);
        Call<ArrayList<Category>> getCategoriesList = apiInterface.getCategoriesList();
        getCategoriesList.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    for (Category category : response.body()) {
                        if (category.getIdCategory() == id) {
                            // Возвращаем операцию через колбэк
                            callback.onCategoryLoaded(category);
                            return;
                        }
                    }
                    // Если операция с заданным id не найдена, вызываем onError
                    callback.onError("Категория не найдена");
                } else {
                    // Обработка ошибки при выполнении запроса
                    callback.onError("Ошибка при выполнении запроса");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                // Обработка ошибки при выполнении запроса
                callback.onError(t.getMessage());
            }
        });
    }

    public static void getCategoryNameForOperation(int idOperation, final CategoryCallBack categoryCallback) {
        getOperationById(idOperation, new OperationCallback() {
            @Override
            public void onOperationLoaded(Operation operation) {
                if (operation != null) {
                    int categoryId = operation.getIdCategory();
                    getCategoryByID(categoryId, new CategoryCallBack() {
                        @Override
                        public void onCategoryLoaded(Category category) {
                            if (category != null) {
                                categoryCallback.onCategoryLoaded(category);
                            } else {
                                categoryCallback.onError("Категория не найдена");
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            categoryCallback.onError(errorMessage);
                        }
                    });
                } else {
                    categoryCallback.onError("Операция не найдена");
                }
            }

            @Override
            public void onError(String errorMessage) {
                categoryCallback.onError(errorMessage);
            }
        });
    }

    public static void getWalletByNameAndIdUser(int idUser, String walletName, final WalletCallBack walletCallBack) {
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        Call<ArrayList<Wallet>> getAllWallets = apiInterface.getWalletList();
        getAllWallets.enqueue(new Callback<ArrayList<Wallet>>() {
            @Override
            public void onResponse(Call<ArrayList<Wallet>> call, Response<ArrayList<Wallet>> response) {
                if (response.isSuccessful()) {
                    for (Wallet wallet : response.body()) {
                        if (wallet.getIdUser() == idUser) {
                            if (wallet.getName().equalsIgnoreCase(walletName)) {
                                walletCallBack.onWalletLoaded(wallet);
                                return;
                            }
                        }
                    }
                    // Если операция с заданным id не найдена, вызываем onError
                    walletCallBack.onError("Кошелек не найден");
                } else {
                    // Обработка ошибки при выполнении запроса
                    walletCallBack.onError("Ошибка при выполнении запроса");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Wallet>> call, Throwable t) {
                walletCallBack.onError(t.getMessage());
            }
        });

    }

    public static void getCategoryIDByName(String categoryName, final CategoryCallBack categoryCallBack) {
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        Call<ArrayList<Category>> getCategories = apiInterface.getCategoriesList();
        getCategories.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    for (Category category : response.body()) {
                        if (category.getName().equalsIgnoreCase(categoryName)) {
                            categoryCallBack.onCategoryLoaded(category);
                            return;
                        }
                    }
                    categoryCallBack.onError("Категория не найдена");
                } else
                    categoryCallBack.onError("Ошибка при выполнении запроса");
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                categoryCallBack.onError(t.getMessage());
            }
        });
    }

    public static void getWalletByID(int walletId, final WalletCallBack walletCallBack) {
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        Call<ArrayList<Wallet>> getWallets = apiInterface.getWalletList();
        getWallets.enqueue(new Callback<ArrayList<Wallet>>() {
            @Override
            public void onResponse(Call<ArrayList<Wallet>> call, Response<ArrayList<Wallet>> response) {
                if (response.isSuccessful()) {
                    for (Wallet wallet : response.body()) {
                        if (wallet.getIdWallet() == walletId) {
                            walletCallBack.onWalletLoaded(wallet);
                            return;
                        }
                    }
                    walletCallBack.onError("Кошелёк не найден");
                } else
                    walletCallBack.onError("Ошибка при выполнении запроса");
            }

            @Override
            public void onFailure(Call<ArrayList<Wallet>> call, Throwable t) {
                walletCallBack.onError(t.getMessage());
            }
        });
    }

}
