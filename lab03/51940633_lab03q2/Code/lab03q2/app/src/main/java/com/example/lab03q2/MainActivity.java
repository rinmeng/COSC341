package com.example.lab03q2;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<String>  cities;
    ListView listView;
    SearchView searchView;
    ArrayAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // init
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        cities = new ArrayList<>();

        cities.add("Vancouver");
        cities.add("Banff");
        cities.add("Victoria");
        cities.add("Kelowna");
        cities.add("Peachland");

        cities.add("Regina");
        cities.add("Calgary");
        cities.add("Duncan");
        cities.add("Vernon");
        cities.add("West Kelowna");

        cities.add("Salmon Arms");
        cities.add("Enderby");
        cities.add("Hope");
        cities.add("Prince George");
        cities.add("Halifax");

        Collections.sort(cities);

        // set an adapter for the search query
        ad = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, cities);
        listView.setAdapter(ad);


        // filter through the list
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                ad.getFilter().filter(q);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the correct item from the adapter instead of the original cities list
            String selectedcities = (String) listView.getAdapter().getItem(position);
            Toast.makeText(MainActivity.this, "You clicked on " + selectedcities, Toast.LENGTH_SHORT).show();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}