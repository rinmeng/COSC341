package com.example.lab2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText fName, fEmail, fBirthday, fPassword;
    RadioGroup rgGender;
    RadioButton selectedGender;
    Button bRegister;

    int[] validity = new int[5];


    public boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailPattern);
    }

    public boolean isValidPassword(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*[@#$%^&+=!].*") && password.matches(".*[0-9]*");
    }


    public void print(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    public void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    String birthdayText = dayOfMonth + "-" + (month1 + 1) + "-" + year1;
                    fBirthday.setText(birthdayText);
                },
                year, month, day
        );
        datePickerDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fName = findViewById(R.id.name);
        fEmail = findViewById(R.id.fEmail);
        rgGender = findViewById(R.id.rgGender);
        fBirthday = findViewById(R.id.fBirthday);
        fPassword = findViewById(R.id.fPassword);
        bRegister = findViewById(R.id.bRegister);


        fBirthday.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        bRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = fName.getText().toString().trim();
                String email = fEmail.getText().toString().trim();
                selectedGender = findViewById(rgGender.getCheckedRadioButtonId());
                String gender = (selectedGender == null ? "" : selectedGender.getText().toString()).trim();
                String birthday = fBirthday.getText().toString().trim();
                String password = fPassword.getText().toString().trim();


                if (username.isEmpty()){
                    print("Please enter a name!");
                }else if (email.isEmpty() || !isValidEmail(email)) {
                    if (email.isEmpty()){
                        print("Please enter an email address!");
                    }else{
                        print("Please enter a valid email address!");
                    }
                } else if (gender.isEmpty()){
                    print("Please select a gender!");
                } else if (birthday.isEmpty()){
                    print("Please select a birthdate!");
                } else if (password.isEmpty() || password.length() < 8|| !isValidPassword(password)){
                    if (password.isEmpty() || password.length() < 8 ) {
                        print("Password should be 8 characters long!");
                    }else{
                        print("Password should contain 1 numeric digit, 1 uppercase letter, and 1 special character");
                    }
                }else {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("username", username);
                    intent.putExtra("email", email);
                    intent.putExtra("gender", gender);
                    intent.putExtra("birthday", birthday);
                    startActivity(intent);
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}