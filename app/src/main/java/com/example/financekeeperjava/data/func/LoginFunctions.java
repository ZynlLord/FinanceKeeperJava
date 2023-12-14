package com.example.financekeeperjava.data.func;

import android.util.Log;

import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.model.Category;
import com.example.financekeeperjava.data.model.User;

import java.util.ArrayList;

import io.paperdb.Paper;
import retrofit2.Call;

public class LoginFunctions {


    public static boolean validateLoginAndPassword(String login, String password) { // Метод для валидации логина и пароля
        // Валидация логина
        if (login.length() < 8) {
            return false;
        }
        if (!login.matches(".*[A-Z].*")) {
            return false;
        }
        if (!login.matches(".*\\d.*")) {
            return false;
        }

        // Валидация пароля
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }

        return true;
    }

    public static boolean validateLogin(String login) {
        if (login.length() < 8) {
            return false;
        }
        if (!login.matches(".*[A-Z].*")) {
            return false;
        }
        if (!login.matches(".*\\d.*")) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }
        return true;
    }


    public static User findUser(String login, String password, ArrayList<User> users) { // Метод для поиска пользователя среди списка, полученного из API
        for (User user :
                users) {
            if (user.getLogin().equals(login)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    public static User findUserByID(int id, ArrayList<User> users) {
        for (User user : users) {
            if (user.getIdUser() == id) {
                return user;
            }
        }
        return null;
    }

    public static void saveUser(User user) {
        try {
            Paper.book("Account").write("login", user);
        } catch (Exception e) {
            Log.e("LoginFunctions", "Error while saving user: " + e.getMessage());
        }
    }

    public static boolean isLogin() {
        User user = Paper.book("Account").read("login");
        if (user != null) {
            Log.d("Account", "Loggined in:" + user.getLogin());
            return true;
        } else {
            Log.d("Account", "No login");
            return false;
        }
    }

    public static void logout() {
        Paper.book("Account").delete("login");
        Log.d("Account", "log out");
    }

    public static User getLoggedUser() {
        return Paper.book("Account").read("login");
    }


}
