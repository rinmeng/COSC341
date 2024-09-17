package com.example.lab0;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.*;
import android.view.*;


public class MainActivity extends AppCompatActivity {

    private EditText textName;
    private EditText textEmailAddress;
    private RadioGroup radioGroup;
    private CheckBox emailSubscription;
    private TextView textOutput;
    public Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        textName = findViewById(R.id.textName);
        textEmailAddress = findViewById(R.id.textEmailAddress);
        radioGroup = findViewById(R.id.radioGroup);
        emailSubscription = findViewById(R.id.emailSubscription);
        textOutput = findViewById(R.id.textOutput);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = textName.getText().toString().trim();
                String email = textEmailAddress.getText().toString().trim();

                int selectedGenderId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedGenderButton = findViewById(selectedGenderId);
                String gender = selectedGenderButton != null ? selectedGenderButton.getText().toString() : "Not selected";

                boolean isSubscribed = emailSubscription.isChecked();
                String outputMessage = "Name: " + name + "\nEmail: " + email +
                        "\nGender: " + gender + "\nSubscribed: " + (isSubscribed ? "Yes" : "No");

                textOutput.setText(outputMessage);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}