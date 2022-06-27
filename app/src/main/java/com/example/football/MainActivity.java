package com.example.football;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.football.databinding.ActivityMainBinding;
import com.example.football.ui.acheivments.AcheivmentFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

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

        Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(e-> {
            switchActivitiesWithData();
            setContentView(R.layout.activity_tabbed);
        });
    }

}