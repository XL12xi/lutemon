package com.example.lutemon;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LutemonAdapter adapter;
    private Button btnTrainAll, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        btnBack = findViewById(R.id.btnBackToMain);
        btnBack.setOnClickListener(v -> finish());

        HashMap<Integer, Lutemon> trainingMap = Storage.getInstance().getTrainingLutemons();
        ArrayList<Integer> ids = new ArrayList<>(trainingMap.keySet());

        adapter = new LutemonAdapter(ids, trainingMap);
        adapter.setShowTransferButtons(false);
        adapter.setShowReturnButton(true);

        recyclerView = findViewById(R.id.recyclerViewTraining);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnTrainAll = findViewById(R.id.btnTrainAll);
        btnTrainAll.setOnClickListener(v -> {
            for (Lutemon l : trainingMap.values()) {
                l.gainExperience();
            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "All Lutemons trained!", Toast.LENGTH_SHORT).show();
        });
    }
}
