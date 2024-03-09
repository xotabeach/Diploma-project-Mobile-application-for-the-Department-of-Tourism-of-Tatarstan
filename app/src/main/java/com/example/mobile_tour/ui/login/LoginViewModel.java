package com.example.mobile_tour.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;

import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.data.LoginRepository;
import com.example.mobile_tour.data.Result;
import com.example.mobile_tour.data.model.LoggedInUser;
import com.example.mobile_tour.R;
import com.example.mobile_tour.ui.profile.ProfileViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private ProfileViewModel profileViewModel = new ProfileViewModel();
    private Context context;

    public LoginViewModel(LoginRepository loginRepository, Context context) {
        this.loginRepository = loginRepository;
        this.context = context;
    }

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));

            //profileViewModel.setEmail(data.getDisplayName());
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void register(String email, String password, String name) {
        // Здесь можно выполнять операции для регистрации нового пользователя
        // Например:
        Result<LoggedInUser> result = loginRepository.register(email, password, name);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));

        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}