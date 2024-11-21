package com.example.lab4;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadActivity extends AppCompatActivity {
    Button btnMain, btnPrev, btnNext;
    TextView studentId, studentName, studentGender, studentDivision;
    JSONArray studentDataArray;
    int currentIndex = 0;
    JSONArray fireBaseStudents = new JSONArray();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference studentsRef = database.getReference("students");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read);

        btnMain = findViewById(R.id.btnMain);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        studentId = findViewById(R.id.studentId);
        studentName = findViewById(R.id.studentName);
        studentGender = findViewById(R.id.studentGender);
        studentDivision = findViewById(R.id.studentDivision);

        // Initialize the studentDataArray with local data (if available)
        studentDataArray = readDataFromFile();

        // Immediately display the first student (if there's any data)
        if (studentDataArray.length() > 0) {
            try {
                JSONObject currentStudent = studentDataArray.getJSONObject(0);
                updateStudentDetails(
                        currentStudent.getString("studentId"),
                        currentStudent.getString("firstName"),
                        currentStudent.getString("lastName"),
                        currentStudent.getString("gender"),
                        currentStudent.getString("division")
                );
                btnPrev.setEnabled(false); // Disable previous button if we are at the start
                btnNext.setEnabled(studentDataArray.length() > 1); // Enable next if more than one item
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Fetch Firebase data asynchronously
        fetchDataFromFirebase();

        // After, if the data is empty, display a message and set both of the buttons to display false
        TextView mess = findViewById(R.id.mess);
        TextView stid,stn,stg,std;
        stid = findViewById(R.id.textView);
        stn = findViewById(R.id.textView2);
        stg = findViewById(R.id.textView4);
        std = findViewById(R.id.textView5);
        if (studentDataArray.length() == 0){
            mess.setText("Please start writing data.");
            stid.setText("");
            stn.setText("");
            stg.setText("");
            std.setText("");
            btnNext.setEnabled(false);
            btnPrev.setEnabled(false);
        }else{
            mess.setText("");
            stid.setText("Student Number");
            stn.setText("Name");
            stg.setText("Gender");
            std.setText("Division");
        }

        // Button actions
        btnMain.setOnClickListener(view -> finish());

        btnNext.setOnClickListener(view -> {
            if (currentIndex < studentDataArray.length() - 1) {
                currentIndex++;
                btnPrev.setEnabled(true);
                btnNext.setEnabled(currentIndex < studentDataArray.length() - 1);
                try {
                    JSONObject currentStudent = studentDataArray.getJSONObject(currentIndex);
                    updateStudentDetails(
                            currentStudent.getString("studentId"),
                            currentStudent.getString("firstName"),
                            currentStudent.getString("lastName"),
                            currentStudent.getString("gender"),
                            currentStudent.getString("division")
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "No Next Students To Show", Toast.LENGTH_SHORT).show();
            }
        });

        btnPrev.setOnClickListener(view -> {
            if (currentIndex > 0) {
                currentIndex--;
                btnNext.setEnabled(true);
                btnPrev.setEnabled(currentIndex > 0);
                try {
                    JSONObject currentStudent = studentDataArray.getJSONObject(currentIndex);
                    updateStudentDetails(
                            currentStudent.getString("studentId"),
                            currentStudent.getString("firstName"),
                            currentStudent.getString("lastName"),
                            currentStudent.getString("gender"),
                            currentStudent.getString("division")
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "No Previous Students To Show", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataFromFirebase() {
        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Fetch Firebase data and append it to the local data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JSONObject studentObject = new JSONObject();
                    try {
                        studentObject.put("studentId", snapshot.child("studentId").getValue(String.class));
                        studentObject.put("firstName", snapshot.child("firstName").getValue(String.class));
                        studentObject.put("lastName", snapshot.child("lastName").getValue(String.class));
                        studentObject.put("gender", snapshot.child("gender").getValue(String.class));
                        studentObject.put("division", snapshot.child("division").getValue(String.class));
                        fireBaseStudents.put(studentObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Append Firebase data to the existing local data
                appendFirebaseToMainArray();

                // Update the UI after Firebase data is appended
                updateUIWithCombinedData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void updateUIWithCombinedData() {
        // After Firebase data is fetched and combined, update the UI
        if (studentDataArray.length() > 0) {
            try {
                JSONObject currentStudent = studentDataArray.getJSONObject(0);
                updateStudentDetails(
                        currentStudent.getString("studentId"),
                        currentStudent.getString("firstName"),
                        currentStudent.getString("lastName"),
                        currentStudent.getString("gender"),
                        currentStudent.getString("division")
                );
                btnPrev.setEnabled(false); // Disable previous button if we are at the start
                btnNext.setEnabled(studentDataArray.length() > 1); // Enable next if more than one item
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateStudentDetails(String stI, String stFN, String stLN, String stGD, String stDVS) {
        studentId.setText(stI);
        String stN = stFN + " " + stLN;
        studentName.setText(stN);
        studentGender.setText(stGD);
        studentDivision.setText(stDVS);
    }

    private void appendFirebaseToMainArray() {
        for (int i = 0; i < fireBaseStudents.length(); i++) {
            try {
                // Get each JSON object from the Firebase students array
                JSONObject student = fireBaseStudents.getJSONObject(i);
                // Append it to the main studentDataArray
                studentDataArray.put(student);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean doesFileExist(String fileName) {
        File file = new File(getFilesDir(), fileName);
        return file.exists();
    }

    private JSONArray readDataFromFile() {
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

}

