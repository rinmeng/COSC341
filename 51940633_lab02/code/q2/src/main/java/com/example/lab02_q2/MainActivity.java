package com.example.lab02_q2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button bWebsite, bMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bWebsite = findViewById(R.id.bWebsite);
        bMap = findViewById(R.id.bMap);

        bWebsite.setOnClickListener(view -> {
            Intent webView = new Intent(MainActivity.this, WebsiteActivity.class);
            startActivity(webView);
        });
        bMap.setOnClickListener(view -> {
            Intent mapView = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(mapView);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}