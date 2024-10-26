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

public class MainActivity2 extends AppCompatActivity {
    Button bToQ1, bSummary;
    Spinner s1, s2, s3, s4;
    TextView selectionOutput;
    String selectedText;
    ArrayList<String> selectedValues = new ArrayList<>();

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
            public void onClick(View view)
                selectedValues.clear();

                // Initialize counters
                int coscCount = 0;
                int phyCount = 0;
                int level100Count = 0;
                int level200Count = 0;
                int level300Count = 0;
                int level400Count = 0;

                String s1Value = s1.getSelectedItem().toString();
                String s2Value = s2.getSelectedItem().toString();
                String s3Value = s3.getSelectedItem().toString();
                String s4Value = s4.getSelectedItem().toString();

                selectedValues.add(s1Value);
                selectedValues.add(s2Value);
                selectedValues.add(s3Value);
                selectedValues.add(s4Value);

                for (String value : selectedValues) {
                    if (value.startsWith("COSC")) {
                        coscCount++;
                    } else if (value.startsWith("PHY")) {
                        phyCount++;
                    }

                    if (value.matches(".*\\b100\\b.*")) {
                        level100Count++;
                    } else if (value.matches(".*\\b200\\b.*")) {
                        level200Count++;
                    } else if (value.matches(".*\\b300\\b.*")) {
                        level300Count++;
                    } else if (value.matches(".*\\b400\\b.*")) {
                        level400Count++;
                    }
                }

                selectedText = "You have selected: \n" +
                        " - " + coscCount + " COSC Courses\n" +
                        " - " + phyCount + " PHY Courses\n" +
                        " - " + level100Count + " from 100 level\n" +
                        " - " + level200Count + " from 200 level\n" +
                        " - " + level300Count + " from 300 level\n" +
                        " - " + level400Count + " from 400 level";

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
