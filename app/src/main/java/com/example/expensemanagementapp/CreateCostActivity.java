package com.example.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateCostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cost);

        Intent intent = getIntent();
        Integer trip_id = intent.getIntExtra("id", 0);
        String trip_name = intent.getStringExtra("name");

        EditText tripName = findViewById(R.id.textTripName);
        tripName.setText(trip_name);
        tripName.setEnabled(false);
        EditText description = findViewById(R.id.textDescription);
        EditText time = findViewById(R.id.textTime);
        String now = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        time.setText(now);
        time.setEnabled(false);
        Spinner spinnerType = findViewById(R.id.spinnerType);
        View backToListCosts = findViewById(R.id.backToListCosts);

        List<String> typeSelect = new ArrayList<String>();
        typeSelect.add("Moving expenses");
        typeSelect.add("Food cost");
        typeSelect.add("Hotel expenses");
        typeSelect.add("Costs incurred");

        ArrayAdapter<String> dataType =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeSelect);
        spinnerType.setAdapter(dataType);

        EditText amount = findViewById(R.id.textAmount);
        Button createCost = findViewById(R.id.buttonCreateCost);

        createCost.setOnClickListener(e -> {
            DatabaseHelper db = new DatabaseHelper(this);
            db.insertCost(new CostModelClass(spinnerType.getSelectedItem().toString(),
                    Integer.parseInt(amount.getText().toString()), description.getText().toString(), time.getText().toString(), trip_id));
            Intent intentAllCost = new Intent(CreateCostActivity.this, ViewAllCostActivity.class);
            intentAllCost.putExtra("name", trip_name);
            intentAllCost.putExtra("id", trip_id);
            startActivity(intentAllCost);
        });

        backToListCosts.setOnClickListener(v -> {
            Intent intentAllCost = new Intent(CreateCostActivity.this, ViewAllCostActivity.class);
            intentAllCost.putExtra("name", trip_name);
            intentAllCost.putExtra("id", trip_id);
            startActivity(intentAllCost);
        });
    }
}