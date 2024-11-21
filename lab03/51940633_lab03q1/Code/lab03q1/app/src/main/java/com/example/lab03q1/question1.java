package com.example.lab03q1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class question1 extends AppCompatActivity {

    // init
    ArrayList<String> questionsArr = new ArrayList<>();
    String[] choices = new String[]{};
    String selectedToText = "";
    int correctAnswers = 0;
    String btnText = "Next";
    int index = 0;

    // set up for the actual answer's index and images
    ArrayList<String[]> carLogosChoices = new ArrayList<>();
    int[] carLogosImages = new int[]{
            R.drawable.logo_quiz_bmw,
            R.drawable.logo_quiz_infiniti,
            R.drawable.logo_quiz_toyota,
            R.drawable.logo_quiz_volkswagen
    };
    int[] carDivisionAns = new int[]{3,2,1,0};
    ArrayList<String[]> carDivisionChoices = new ArrayList<>();
    int[] carDivisionImages = new int[]{
            R.drawable.division_amg,
            R.drawable.division_gazoo_racing,
            R.drawable.division_namyang,
            R.drawable.division_rs
    };
    int[] carLogosAns = new int[]{0,1,2,3};
    ArrayList<String[]> carModelChoices = new ArrayList<>();
    int[] carModelImages = new int[]{
            R.drawable.model_quiz_audi,
            R.drawable.model_quiz_infiniti,
            R.drawable.model_quiz_porsche,
            R.drawable.model_quiz_toyota,
    };
    int[] carModelAns = new int[]{1,1,1,1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question1);

        // init
        correctAnswers = 0;

        TextView q1Text = findViewById(R.id.q1);
        TextView q1Desc = findViewById(R.id.q1desc);
        ImageView imgDisplay = findViewById(R.id.imgDisplay);
        RadioGroup options = findViewById(R.id.radioGroup);
        Button option1 = findViewById(R.id.option1);
        Button option2 = findViewById(R.id.option2);
        Button option3 = findViewById(R.id.option3);
        Button option4 = findViewById(R.id.option4);
        Button nextButton = findViewById(R.id.nextButton);

        int numOfQuestions = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("strNumOfQuestions")));
        String category = getIntent().getStringExtra("strCategory");
        String q1DescString = "Select the correct answer for this " + category.toLowerCase();
        q1Desc.setText(q1DescString);

        setupQuestionChoices();
        nextButton.setText(btnText);

        // Load the first question
        loadQuestion(q1Text, imgDisplay, options, option1, option2, option3, option4, category);

        // update the btn text to finish if there is only 1 questions
        if (numOfQuestions == 1) {
            btnText = "Finish";
            nextButton.setText(btnText);
        }

        // if next/finish button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the id of the selected radio button
                int selectedId = options.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    // find the selected radio button's text
                    RadioButton selected = findViewById(selectedId);
                    selectedToText = selected.getText().toString();

                    // set up for the actual correct index that we init'd
                    int ansIndex;
                    switch (category) {
                        case "Car Logo":
                            ansIndex = carLogosAns[index]; // Correct index for Car Logo
                            break;
                        case "Car Division":
                            ansIndex = carDivisionAns[index]; // Correct index for Car Division
                            break;
                        case "Car Model":
                            ansIndex = carModelAns[index]; // Correct index for Car Model
                            break;
                        default:
                            ansIndex = -1; // Default value in case of unexpected category
                    }

                    // if selected answer is the same as the actual answer
                    if (selectedToText.equals(choices[ansIndex])) {
                        correctAnswers++;
                    }

                    // debug
                    Log.d("Debug", "your input: " + selectedToText + " real: " + choices[ansIndex] + " total Correct: " + correctAnswers);

                    // Increment the index to go to the next question
                    index++;
                    // if we are still able to increment to the next question, then load question.
                    if (index < numOfQuestions) {
                        loadQuestion(q1Text, imgDisplay, options, option1, option2, option3, option4, category);

                        // Update the button text for the last question
                        if (index == numOfQuestions - 1) {
                            btnText = "Finish";
                            nextButton.setText(btnText);
                        }
                    } else {
                        // End of questions, proceed with finish actions
                        Intent it = new Intent(question1.this, FinishActivity.class);
                        it.putExtra("score",  "You scored: " + correctAnswers + " out of " + numOfQuestions);
                        startActivity(it);
                        finish(); // or other end actions
                    }
                } else {
                    Toast.makeText(question1.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadQuestion(TextView q1Text, ImageView imgDisplay, RadioGroup options,
                              Button option1, Button option2, Button option3, Button option4, String category) {

        q1Text.setText("Question " + (index + 1) + " in " + category);
        // Clear previous selection
        options.clearCheck();

        // get the options from the array lists
        switch (Objects.requireNonNull(category)) {
            case "Car Logo":
                imgDisplay.setImageResource(carLogosImages[index]);
                choices = carLogosChoices.get(index);
                break;
            case "Car Division":
                imgDisplay.setImageResource(carDivisionImages[index]);
                choices = carDivisionChoices.get(index);
                break;
            case "Car Model":
                imgDisplay.setImageResource(carModelImages[index]);
                choices = carModelChoices.get(index);
                break;
        }

        // set the options
        option1.setText(choices[0]);
        option2.setText(choices[1]);
        option3.setText(choices[2]);
        option4.setText(choices[3]);
    }

    public void setupQuestionChoices() {
        carLogosChoices.add(new String[]{"BMW", "Kia", "Mercedes-Benz", "Porsche"});
        carLogosChoices.add(new String[]{"Lincoln", "Infiniti", "Chrysler", "Buick"});
        carLogosChoices.add(new String[]{"Suzuki", "Nissan", "Toyota", "Honda"});
        carLogosChoices.add(new String[]{"Subaru", "Dodge", "Mazzanti", "Volkswagen"});

        carModelChoices.add(new String[]{"Kia", "Audi", "Mercedes-Benz", "Porsche"});
        carModelChoices.add(new String[]{"Lincoln", "Infiniti", "Chrysler", "Buick"});
        carModelChoices.add(new String[]{"Suzuki", "Porsche", "Nissan", "Honda"});
        carModelChoices.add(new String[]{"Subaru", "Toyota", "Mazzanti", "Dodge"});

        carDivisionChoices.add(new String[]{"Aufrecht Monder Großaspach", "Aufrecht Malco Großaspach",
                 "Aufrecht Messie Großaspach","Aufrecht Melcher Großaspach"});
        carDivisionChoices.add(new String[]{"Gazoo Tel", "Gazoo Track Racing", "Gazoo Racing", "Gazoo Rover" });
        carDivisionChoices.add(new String[]{"Nyeon", "Namyang", "Nannyeon", "Nieun"});
        carDivisionChoices.add(new String[]{"RennSport", "ReinnSport", "Racing Sport", "Renn Speed"});
    }
}
