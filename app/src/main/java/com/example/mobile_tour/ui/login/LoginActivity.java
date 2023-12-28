package com.example.mobile_tour.ui.login;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.ActivityMainBinding;
import com.example.mobile_tour.ui.login.LoginViewModel;
import com.example.mobile_tour.ui.login.LoginViewModelFactory;
import com.example.mobile_tour.databinding.ActivityLoginBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginActivity extends AppCompatActivity {

    protected void hideSystemBars(){

        View decorView = getWindow().getDecorView();


        // Сделаем верхнюю и нижнюю панели прозрачными и растянем экран до границы
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = decorView.getWindowInsetsController();
            if (controller != null) {
                controller.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                controller.show(WindowInsets.Type.navigationBars());
            }
        } else {
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }

        decorView.setSystemUiVisibility(flags);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }




    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        hideSystemBars();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final EditText nameEditText = binding.name;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button registerButton = binding.textView2;
        final TextView welcomeText =  binding.textView;
        final Button buttonBack = binding.buttonback;
        final TextView  regText = binding.textView5;


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        nameEditText.setAlpha(0);
        buttonBack.setAlpha(0);
        regText.setAlpha(0);

        final float originalUsernameY = usernameEditText.getY();
        final float originalWelcomeTextY = welcomeText.getY();
        final float originalNameY = nameEditText.getY();
        final float originalRegTextY = regText.getY();

        final float[] data = { };
        //int name_data = (int) nameEditText.getY();
        //int welc_data = (int) welcomeText.getY();
        //int us_data = usernameEditText.getY();



        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
                float user1Y = usernameEditText.getY();

                /*data[0] = user1Y;
                data[1] = (float) nameEditText.getY();
                data[2] = (float) welcomeText.getY();*/

                ObjectAnimator usernameAnim = ObjectAnimator.ofFloat(usernameEditText, "y", user1Y, (float) (user1Y+(size.y*0.02)));
                usernameAnim.setDuration(1000);
                usernameAnim.start();


                ObjectAnimator welcomeTextAnim = ObjectAnimator.ofFloat(welcomeText, "y", welcomeText.getY(), (float)(welcomeText.getY()-(size.y*0.22)));
                welcomeTextAnim.setDuration(1000);
                welcomeTextAnim.start();


                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
                PropertyValuesHolder yPosition = PropertyValuesHolder.ofFloat("y", nameEditText.getY(), nameEditText.getY() + (size.y * 0.024f));

                ObjectAnimator combinedAnim = ObjectAnimator.ofPropertyValuesHolder(nameEditText, alpha, yPosition);
                combinedAnim.setDuration(1000);
                combinedAnim.start();

                PropertyValuesHolder alphaRegText = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
                PropertyValuesHolder yPositionRegText = PropertyValuesHolder.ofFloat("y", nameEditText.getY(), nameEditText.getY() - (size.y * 0.028f));

                ObjectAnimator combinedAnimRegText = ObjectAnimator.ofPropertyValuesHolder(regText, alphaRegText, yPositionRegText);
                combinedAnimRegText.setDuration(1000);
                combinedAnimRegText.start();
                //usernameEditText.setY((float) (user1Y+(size.y*0.02)));
                //passwordEditText.setY((float) (passw1Y+(size.y*0.1)));
                //welcomeText.setY((float)(welcomeText.getY()-(size.y*0.23)));
                registerButton.setVisibility(View.GONE);
                buttonBack.setAlpha(1);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /*ObjectAnimator usernameAnim = ObjectAnimator.ofFloat(usernameEditText, "y", usernameEditText.getY(), originalUsernameY*(-1));
                usernameAnim.setDuration(1000);
                usernameAnim.start();


                ObjectAnimator welcomeTextAnim = ObjectAnimator.ofFloat(welcomeText, "y", welcomeText.getY(), originalWelcomeTextY*(-1));
                welcomeTextAnim.setDuration(1000);
                welcomeTextAnim.start();


                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
                PropertyValuesHolder yPosition = PropertyValuesHolder.ofFloat("y", nameEditText.getY(), originalNameY*(-1));

                ObjectAnimator combinedAnim = ObjectAnimator.ofPropertyValuesHolder(nameEditText, alpha, yPosition);
                combinedAnim.setDuration(1000);
                combinedAnim.start();*/

                usernameEditText.setTranslationY(originalUsernameY);
                welcomeText.setTranslationY(originalWelcomeTextY);
                nameEditText.setTranslationY(originalNameY);
                nameEditText.setAlpha(0);
                regText.setAlpha(0);
                regText.setTranslationY(originalRegTextY);
                //usernameEditText.setY((float) (user1Y+(size.y*0.02)));
                //passwordEditText.setY((float) (passw1Y+(size.y*0.1)));
                //welcomeText.setY((float)(welcomeText.getY()-(size.y*0.23)));
                registerButton.setVisibility(View.VISIBLE);
                buttonBack.setAlpha(0);
            }
        });
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {




            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);


                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        hideSystemBars();
    }


    @Override
    protected void onResume(){
        super.onResume();
        hideSystemBars();
    }

    @Override
    protected void onStop(){
        super.onStop();
        hideSystemBars();
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}