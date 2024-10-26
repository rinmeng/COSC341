package com.example.lab01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.content.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    Button bToQ1, bSummary;
    Spinner s1, s2, s3, s4;
    TextView selectionOutput;
    String selectedText;
    ArrayList<String> selectedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        bToQ1 = findViewById(R.id.bToQ1);
        bSummary = findViewById(R.id.bSummary);
        s1 = findViewById(R.id.spinner1);
        s2 = findViewById(R.id.spinner2);
        s3 = findViewById(R.id.spinner3);
        s4 = findViewById(R.id.spinner4);
        selectionOutput = findViewById(R.id.selectionOutput);

        bSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedValues.add(s1.getSelectedItem().toString());
                selectedValues.add(s2.getSelectedItem().toString());
                selectedValues.add(s3.getSelectedItem().toString());
                selectedValues.add(s4.getSelectedItem().toString());

                selectedText = "You have selected: \n" + selectedValues.get(0);

                selectionOutput.setText(selectedText);
            }
        });

        bToQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}