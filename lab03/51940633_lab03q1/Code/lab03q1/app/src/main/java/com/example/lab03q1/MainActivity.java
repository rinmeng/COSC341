package com.example.lab03q1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnLoadQuiz = findViewById(R.id.btnLoadQuiz);
        btnLoadQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spNumOfQuestions = findViewById(R.id.spNumOfQuestions);
                Spinner spCategory = findViewById(R.id.spCategory);
                String strNumOfQuestions = spNumOfQuestions.getSelectedItem().toString();
                String strCategory = spCategory.getSelectedItem().toString();

                Intent it = new Intent(MainActivity.this, question1.class);
                it.putExtra("strNumOfQuestions", strNumOfQuestions);
                it.putExtra("strCategory", strCategory);
                startActivity(it);
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}