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

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button bPrev, bNext, bRandom, bToQ2;
    ImageView imgView;
    int imgIndex = 0;
    int lastIndex = 0;
    int[] imgArray = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    TextView imgInfo;
    String textToImgInfo;
    String[] imgTags = {"Pier, Fog, Lake",
            "Lake, Sunset, Reflection",
            "Sunset, Tree, Dawn",
            "Boat, Dusk, Silhouette"};


    public int counterUp() {
        imgIndex = (imgIndex == imgArray.length - 1) ? 0 : imgIndex + 1;
        return imgIndex;
    }

    public int counterDown() {
        imgIndex = (imgIndex == 0) ? imgArray.length - 1 : imgIndex - 1;
        return imgIndex;
    }

    public int counterRandom() {
        // ensure that it is randomized properly, not displaying the same as last one.
        lastIndex = imgIndex;
        do {
            imgIndex = (int) (Math.random() * imgArray.length);
        } while (lastIndex == imgIndex);
        return imgIndex;
    }

    public void showToast() {
        Toast.makeText(this, imgTags[imgIndex], Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bPrev = findViewById(R.id.bPrev);
        bNext = findViewById(R.id.bNext);
        bRandom = findViewById(R.id.bRandom);
        imgView = findViewById(R.id.imgView);
        imgInfo = findViewById(R.id.imgInfo);
        bToQ2 = findViewById(R.id.bToQ2);

        textToImgInfo = "Image: " + (imgIndex + 1) + " of " + imgArray.length;
        imgInfo.setText(textToImgInfo);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgView.setImageResource(imgArray[counterUp()]);
                textToImgInfo = "Image: " + (imgIndex + 1) + " of " + imgArray.length;
                imgInfo.setText(textToImgInfo);
            }
        });
        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgView.setImageResource(imgArray[counterDown()]);
                textToImgInfo = "Image: " + (imgIndex + 1) + " of " + imgArray.length;
                imgInfo.setText(textToImgInfo);
            }
        });
        bRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgView.setImageResource(imgArray[counterRandom()]);
                textToImgInfo = "Image: " + (imgIndex + 1) + " of " + imgArray.length;
                imgInfo.setText(textToImgInfo);
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast();
            }
        });

        bToQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
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