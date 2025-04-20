package com.example.lutemon;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LutemonAdapter adapter;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnBack = findViewById(R.id.btnBackToMain);
        btnBack.setOnClickListener(v -> finish());

        HashMap<Integer, Lutemon> homeMap = Storage.getInstance().getHomeLutemons();
        ArrayList<Integer> ids = new ArrayList<>(homeMap.keySet());

        adapter = new LutemonAdapter(ids, homeMap);
        adapter.setShowTransferButtons(true);
        adapter.setShowReturnButton(false);

        recyclerView = findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
