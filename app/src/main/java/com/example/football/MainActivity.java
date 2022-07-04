package com.example.football;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.football.components.Loading;
import com.example.football.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextView errorLabel;
    EditText login;
    EditText pass;
    TextInputLayout loginLay;
    TextInputLayout passLay;
    View loseFocus;

    private boolean automaticLogin = false;

    public Loading loading;

    public static JSONObject rawUser;

    public void Load() {

        if (loading != null) {
            loading.setVisibility(View.INVISIBLE);
            if (rawUser != null) {
                errorLabel.setText("");
            }
        }
        if (automaticLogin) {
            Pair<Boolean, JSONObject> user = Connection.findUser(login.getText().toString(), pass.getText().toString());
            if (user == null) {
                //onButtonShowPopupWindowClick(findViewById(R.id.imageProf));
            } else {
                Saver.SaveAut(this, login.getText().toString(), pass.getText().toString());
                System.out.println(user.second);
                rawUser = user.second;
                switchActivitiesWithData();
            }
        }
    }

    private void switchActivitiesWithData() {
        Intent switchActivityIntent = new Intent(this, TabbedActivity.class);
        switchActivityIntent.putExtra("message", "From: " + MainActivity.class.getSimpleName());
        startActivity(switchActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Loading gifWebView = findViewById(R.id.loading_gif);
//        gifWebView = new Loading(this,
//                "file:///android_asset/lick.gif");
//        setContentView(gifWebView);

        Connection.getData(this);


        login = findViewById(R.id.login);
        pass = findViewById(R.id.pass);
        loginLay = findViewById(R.id.inputLogin);
        passLay = findViewById(R.id.inputPass);
        loseFocus = findViewById(R.id.loseFocus);

        loading = findViewById(R.id.loading_gif);
        InputStream stream = null;
        try {
            stream = getAssets().open("loading.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
        loading.SetData(stream);
        //loading.setVisibility(View.INVISIBLE);

        loseFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(
                                Activity.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });

        errorLabel = findViewById(R.id.errorText);
        ImageView logo = findViewById(R.id.logo);
        logo.setOnClickListener(e -> {
            errorLabel.setText("");
            login.setText("");
            pass.setText("");
        });

        Button button = findViewById(R.id.loginButton);
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
                    Saver.SaveAut(this, login.getText().toString(), pass.getText().toString());
                }

                if (Connection.canConnect) {
                    Pair<Boolean, JSONObject> user = Connection.findUser(login.getText().toString(), pass.getText().toString());
                    if (user.first) {
                        Saver.SaveAut(this, login.getText().toString(), pass.getText().toString());
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
                        //onButtonShowPopupWindowClick(getLayoutInflater().inflate(R.layout.activity_main,null));
                    }
                } else {
                    errorLabel.setText("Нет подключения к серверу");
                    loading.setVisibility(View.VISIBLE);
                    Connection.getData(this);
                }
            }
        });

        Saver.LoadAut(this);
        System.out.println("--------------------------------------------" + Saver.login + " " + Saver.pass);
        if (!Objects.equals(Saver.login, "") && !Objects.equals(Saver.pass, "")) {
            login.setText(Saver.login);
            pass.setText(Saver.pass);
            errorLabel.setText("");

            automaticLogin = true;
        }


//        login.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!TextUtils.isEmpty(((EditText) v).getText())) {
//                    loginLay.setError(null);
//                    errorLabel.setText(null);
//                } else {
//                    loginLay.setError("WRONG");
//                }
//            }
//        });

        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
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
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                errorLabel.setText("");
                if (!hasFocus) {
                    errorLabel.setText("Пустой пароль");
                    if (!TextUtils.isEmpty(((EditText) v).getText())) {
                        passLay.setError(null);
                        errorLabel.setText(null);
                    }
                }
            }
        });
    }


    public void onButtonShowPopupWindowClick(View view) {
        System.out.println("______________________CANT_CONNECT__________SHOWING_POPUP________________________________________");

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.wrong_credits, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}