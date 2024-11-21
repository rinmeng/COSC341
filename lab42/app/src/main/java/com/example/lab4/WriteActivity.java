package com.example.lab4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity {
    Button btnSubmit;
    EditText studentId, firstName, lastName;
    TextView fileop;
    RadioGroup gender;
    Spinner division;
    boolean writeToDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        // For testing connection, set a simple message in Firebase
        myRef.setValue("Hello, World!");

        fileop = findViewById(R.id.fileop);

        btnSubmit = findViewById(R.id.btnSubmit);
        studentId = findViewById(R.id.studentId);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        gender = findViewById(R.id.gender);
        division = findViewById(R.id.division);

        writeToDatabase = getIntent().getBooleanExtra("writeToDatabase", false);

        String t;
        if (writeToDatabase){
            t = fileop.getText() + " (Writing to Database)";
        } else {
            t = fileop.getText() + " (Writing to Local)";
        }
        fileop.setText(t);

        btnSubmit.setOnClickListener(view -> {

            String studentIdString = studentId.getText().toString();
            String firstNameString = firstName.getText().toString();
            String lastNameString = lastName.getText().toString();
            String selectedGender = "";
            String selectedDivision = division.getSelectedItem().toString();

            if (gender.getCheckedRadioButtonId() != -1){
                selectedGender = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
            }

            if(studentIdString.isEmpty() || !studentIdString.matches("\\d{8}")){
                if (!studentIdString.matches("\\d{8}")){
                    Toast.makeText(this, "Please enter a valid Student ID of 8 digits!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Please enter a Student ID!", Toast.LENGTH_SHORT).show();
                }
            } else if (lastNameString.isEmpty()) {
                Toast.makeText(this, "Please enter your Last Name!", Toast.LENGTH_SHORT).show();
            } else if (firstNameString.isEmpty()){
                Toast.makeText(this, "Please enter your First Name!", Toast.LENGTH_SHORT).show();
            }  else if (selectedGender.isEmpty()){
                Toast.makeText(this, "Please enter your Gender!", Toast.LENGTH_SHORT).show();
            } else {
                if(writeToDatabase){
                    // Write data to Firebase
                    DatabaseReference studentsRef = FirebaseDatabase.getInstance().getReference("students");

                    // Create a Map or Student object to hold the data
                    Map<String, Object> studentData = new HashMap<>();
                    studentData.put("studentId", studentIdString);
                    studentData.put("firstName", firstNameString);
                    studentData.put("lastName", lastNameString);
                    studentData.put("gender", selectedGender);
                    studentData.put("division", selectedDivision);

                    // Push the data to Firebase
                    studentsRef.push().setValue(studentData)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(WriteActivity.this, "Data saved to Firebase successfully!", Toast.LENGTH_SHORT).show();
                                // Navigate back to MainActivity after successful write
                                startActivity(new Intent(WriteActivity.this, MainActivity.class));
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(WriteActivity.this, "Error saving data to Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });

                } else {
                    // Write data to local storage
                    JSONObject jsonData = new JSONObject();
                    try {
                        jsonData.put("studentId", studentIdString);
                        jsonData.put("firstName", firstNameString);
                        jsonData.put("lastName", lastNameString);
                        jsonData.put("gender", selectedGender);
                        jsonData.put("division", selectedDivision);

                        JSONArray existingData = readDataFromFile();
                        existingData.put(jsonData);
                        writeToFile(existingData.toString());
                        Toast.makeText(this, "Data saved locally!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error saving data locally!", Toast.LENGTH_SHORT).show();
                    }
                    Intent in = new Intent(WriteActivity.this, MainActivity.class);
                    startActivity(in);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private JSONArray readDataFromFile(){
        JSONArray data = new JSONArray();
        FileInputStream fis = null;
        try {
            fis = openFileInput("data.json");
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String fileContent = new String(buffer);
            if (!fileContent.isEmpty()) {
                data = new JSONArray(fileContent);
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    private void writeToFile(String data) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("data.json", MODE_PRIVATE);
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
