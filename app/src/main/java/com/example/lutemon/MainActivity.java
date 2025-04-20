package com.example.lutemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCreateLutemon(View view) {
        startActivity(new Intent(this, CreateLutemonActivity.class));
    }

    public void openHome(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void openTraining(View view) {
        startActivity(new Intent(this, TrainingActivity.class));
    }

    public void openBattle(View view) {
        startActivity(new Intent(this, BattleActivity.class));
    }

}
