package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button btnRead, btnWrite;
    CheckBox checkBox;
    private DatabaseReference studentsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);
        checkBox = findViewById(R.id.checkBox);

        boolean writeOnDatabase = checkBox.isChecked();

        btnRead.setOnClickListener(view -> {
            Intent WriteActivity = new Intent(MainActivity.this, ReadActivity.class);
            startActivity(WriteActivity);
        });

        btnWrite.setOnClickListener(view -> {
            Intent mainToWrite = new Intent(MainActivity.this, WriteActivity.class);
            mainToWrite.putExtra("writeToDatabase", checkBox.isChecked());
            startActivity(mainToWrite);

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean doesFileExist(String fileName) {
        File file = new File(getFilesDir(), fileName);
        return file.exists();
    }
}