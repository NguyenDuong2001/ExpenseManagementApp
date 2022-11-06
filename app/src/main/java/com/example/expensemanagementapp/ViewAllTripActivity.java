package com.example.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ViewAllTripActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_trip);

        DatabaseHelper db = new DatabaseHelper(this);
        List<TripModelClass> trips = db.getTrips("");

        ArrayAdapter<TripModelClass> arrayAdapter
                = new ArrayAdapter<TripModelClass>(this, android.R.layout.simple_list_item_1 , trips);
        ListView ListTripAll = findViewById(R.id.listTrips);

        ListTripAll.setAdapter(arrayAdapter);

        ListTripAll.setOnItemClickListener((adapterView, view1, i, l) -> {
            Intent intent = new Intent(ViewAllTripActivity.this, ViewTripDetailActivity.class);
            intent.putExtra("id", trips.get(i).getId());
            startActivity(intent);
        });

        Button buttonCreateTrip = findViewById(R.id.buttonCreateTrip);
        buttonCreateTrip.setOnClickListener(v -> {
            Intent intentCreateTrip = new Intent(ViewAllTripActivity.this, MainActivity.class);
            startActivity(intentCreateTrip);
        });

        EditText search = findViewById(R.id.textSearchTrip);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                List<TripModelClass> trips = db.getTrips(s.toString().trim());
                ArrayAdapter<TripModelClass> arrayAdapter
                        = new ArrayAdapter<TripModelClass>(ViewAllTripActivity.this, android.R.layout.simple_list_item_1 , trips);
                ListView ListTripAll = findViewById(R.id.listTrips);

                ListTripAll.setAdapter(arrayAdapter);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}