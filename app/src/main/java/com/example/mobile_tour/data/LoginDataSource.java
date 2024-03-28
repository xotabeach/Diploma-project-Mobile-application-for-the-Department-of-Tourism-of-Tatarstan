package com.example.mobile_tour.data;

import android.content.Context;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.data.model.LoggedInUser;
import com.example.mobile_tour.ui.profile.ProfileViewModel;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private DataBaseHelper dbHelper; // внедрение зависимости для работы с базой данных

    ProfileViewModel prview = new ProfileViewModel();
    private Context context;

    public LoginDataSource(Context context) {
        this.context = context;
        this.dbHelper = new DataBaseHelper(context);
    }
    public Result<LoggedInUser> login(String email, String password) {
        // Вывод значений переменных в консоль
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // Аутентификация пользователя в БД
        if (dbHelper.isUserExist(email, password)) {
            // Имитация успешной аутентификации
            LoggedInUser user = new LoggedInUser(java.util.UUID.randomUUID().toString(), email);
            prview.setEmail(email);
            return new Result.Success<>(user);

        } else {
            return new Result.Error(new IOException("Invalid username or password"));
        }
    }

    public Result<LoggedInUser> register(String email, String password, String name) {
        // Вывод значений переменных в консоль
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Name: " + name);
        System.out.println("я в дата сорс");
        // Регистрация нового пользователя в БД
            dbHelper.insertUser(email, name, password);
            LoggedInUser registeredUser = new LoggedInUser(java.util.UUID.randomUUID().toString(), email);
            prview.setEmail(email);
            return new Result.Success<>(registeredUser);

    }

    public void logout() {
        // TODO: отзыв аутентификации
    }
}