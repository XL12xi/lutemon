package com.example.lutemon;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CreateLutemonActivity extends AppCompatActivity {

    private EditText etName;
    private RadioGroup colorRadioGroup;
    private Button btnCreate, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        etName = findViewById(R.id.etName);
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        btnCreate = findViewById(R.id.btnCreate);
        btnCancel = findViewById(R.id.btnCancel);

        btnCreate.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            LutemonColor selectedColor = getSelectedColor();

            if (name.isEmpty() || selectedColor == null) {
                Toast.makeText(this, "Please enter a name and select a color", Toast.LENGTH_SHORT).show();
                return;
            }

            Lutemon newLutemon = new Lutemon(name, selectedColor);
            Storage.getInstance().addToHome(newLutemon);
            Toast.makeText(this, "Created: " + newLutemon.getStats(), Toast.LENGTH_SHORT).show();
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private LutemonColor getSelectedColor() {
        int checkedId = colorRadioGroup.getCheckedRadioButtonId();

        if (checkedId == R.id.rbWhite) return LutemonColor.WHITE;
        else if (checkedId == R.id.rbGreen) return LutemonColor.GREEN;
        else if (checkedId == R.id.rbPink) return LutemonColor.PINK;
        else if (checkedId == R.id.rbOrange) return LutemonColor.ORANGE;
        else if (checkedId == R.id.rbBlack) return LutemonColor.BLACK;

        return null;
    }

}
