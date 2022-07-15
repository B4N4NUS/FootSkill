package com.oldi.football;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TextView errorLabel;
    public EditText login;
    public EditText pass;
    private TextInputLayout loginLay;
    private TextInputLayout passLay;
    private ImageView logo;
    private ProgressBar loading;

    // Флаг автоматического входа.
    private boolean automaticLogin = false;

    private static JSONObject rawUser;


    /**
     * Метод обработки конца выгрузки данных с сервера.
     */
    public void Load() {
        if (loading != null) {
            loading.setVisibility(View.INVISIBLE);
            logo.setVisibility(View.VISIBLE);

            if (rawUser != null) {
                errorLabel.setText("");
            }
        }
        if (automaticLogin) {
            Pair<Boolean, JSONObject> user = Connection.findUser(login.getText().toString(), pass.getText().toString(), this);

            if (user != null) {
                SharedPrefLS.SaveAut(this, login.getText().toString(), pass.getText().toString());
                System.out.println(user.second);
                rawUser = user.second;
                switchActivitiesWithData();
            }
        }
    }

    /**
     * Метод переключения между задачами.
     */
    private void switchActivitiesWithData() {
        Intent switchActivityIntent = new Intent(this, TabbedActivity.class);
        switchActivityIntent.putExtra("message", "From: " + MainActivity.class.getSimpleName());
        startActivity(switchActivityIntent);
    }

    /**
     * Метод загрузки данных аутентификации из памяти устройства.
     */
    private void loadSaves() {
        SharedPrefLS.LoadAut(this);

        if (!Objects.equals(SharedPrefLS.login, "") && !Objects.equals(SharedPrefLS.pass, "")) {
            login.setText(SharedPrefLS.login);
            pass.setText(SharedPrefLS.pass);
            errorLabel.setText("");
            automaticLogin = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection.getData(this);

        login = findViewById(R.id.login);
        pass = findViewById(R.id.pass);
        loginLay = findViewById(R.id.inputLogin);
        passLay = findViewById(R.id.inputPass);
        logo = findViewById(R.id.logo);
        loading = findViewById(R.id.loading);
        errorLabel = findViewById(R.id.errorText);

        loadSaves();

        Button button = findViewById(R.id.loginButton);
        View loseFocus = findViewById(R.id.loseFocus);
        ImageView logo = findViewById(R.id.logo);

        loseFocus.setOnClickListener(e -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        logo.setOnClickListener(e -> {
            errorLabel.setText("");
            login.setText("");
            pass.setText("");
        });

        button.setOnClickListener(e -> {
            if (loading.getVisibility() == View.INVISIBLE) {
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                if (login.getText().toString().equals("")) {
                    login.startAnimation(shake);
                    errorLabel.setText("Пустой номер");
                    return;
                }
                if (pass.getText().toString().equals("")) {
                    pass.startAnimation(shake);
                    errorLabel.setText("Пустой пароль");
                    return;
                }

                if (login.getText().toString().equals("123123") && pass.getText().toString().equals("123123")) {
                    switchActivitiesWithData();
                    SharedPrefLS.SaveAut(this, login.getText().toString(), pass.getText().toString());
                }

                if (Connection.canConnect) {
                    Pair<Boolean, JSONObject> user = Connection.findUser(login.getText().toString(), pass.getText().toString(), this);

                    if (user != null) {
                        if (user.first) {
                            SharedPrefLS.SaveAut(this, login.getText().toString(), pass.getText().toString());
                            System.out.println(user.second);
                            rawUser = user.second;
                            switchActivitiesWithData();
                            //setContentView(R.layout.activity_tabbed);
                        } else {
                            login.setText("");
                            pass.setText("");

                            login.startAnimation(shake);
                            pass.startAnimation(shake);

                            errorLabel.setText("Пользователь не найден");
                        }
                    }
                } else {
                    errorLabel.setText("Нет подключения к серверу");
                    loading.setVisibility(View.VISIBLE);
                    Connection.getData(this);
                }
            }
        });

        login.setOnFocusChangeListener((v, hasFocus) -> {
            errorLabel.setText("");
            if (!hasFocus) {
                errorLabel.setText("Пустой номер");
                if (!TextUtils.isEmpty(((EditText) v).getText())) {
                    loginLay.setError(null);
                    errorLabel.setText(null);
                } else {
                    loginLay.setError("WRONG");
                }
            }
        });

        pass.setOnFocusChangeListener((v, hasFocus) -> {
            errorLabel.setText("");
            if (!hasFocus) {
                errorLabel.setText("Пустой пароль");
                if (!TextUtils.isEmpty(((EditText) v).getText())) {
                    passLay.setError(null);
                    errorLabel.setText(null);
                }
            }
        });
    }
}