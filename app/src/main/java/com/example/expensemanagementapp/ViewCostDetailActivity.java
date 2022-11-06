package com.example.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewCostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cost_detail);

        Intent intent = getIntent();
        Integer cost_id = intent.getIntExtra("id", 0);
        Integer trip_id = intent.getIntExtra("trip_id", 0);
        String trip_name = intent.getStringExtra("trip_name");

        View backToListCosts = findViewById(R.id.backToListCosts2);
        EditText tripName = findViewById(R.id.textTripName);
        tripName.setText(trip_name);
        tripName.setEnabled(false);
        EditText description = findViewById(R.id.textCostDescription);
        EditText amount = findViewById(R.id.textCostAmount);
        EditText time = findViewById(R.id.textCostTime);
        time.setEnabled(false);
        Spinner type = findViewById(R.id.spinnerCostType);


        List<String> typeSelect = new ArrayList<String>();
        typeSelect.add("Moving expenses");
        typeSelect.add("Food cost");
        typeSelect.add("Hotel expenses");
        typeSelect.add("Costs incurred");

        ArrayAdapter<String> dataType =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeSelect);
        type.setAdapter(dataType);

        DatabaseHelper db = new DatabaseHelper(this);
        CostModelClass cost = db.getCost(cost_id);
        description.setText(cost.getDescription());
        amount.setText(cost.getAmount().toString());
        time.setText(cost.getTime());

        Integer typeSelected = 0;
        if (Objects.equals(cost.getType(), "Food cost")) {
            typeSelected = 1;
        }

        if (Objects.equals(cost.getType(), "Hotel expenses")) {
            typeSelected = 2;
        }

        if (Objects.equals(cost.getType(), "Costs incurred")) {
            typeSelected = 3;
        }
        type.setSelection(typeSelected);

        Button update = findViewById(R.id.buttonUpdateCost);
        update.setOnClickListener(v -> {
            if(description.getText().toString().matches("")){
                Toast.makeText(this, "Field description is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(amount.getText().toString().matches("")){
                Toast.makeText(this, "Field amount is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            db.updateCost(new CostModelClass(
                    cost_id,
                    type.getSelectedItem().toString(),
                    Integer.parseInt(amount.getText().toString()),
                    time.getText().toString(),
                    description.getText().toString(),
                    trip_id
            ));
            Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show();

        });

        Button delete = findViewById(R.id.buttonDeleteCost);
        delete.setOnClickListener(v -> {
            db.deleteCost(cost_id);
            Toast.makeText(this, "Delete successfully!", Toast.LENGTH_SHORT).show();

            Intent intentAllCost = new Intent(ViewCostDetailActivity.this, ViewAllCostActivity.class);
            intentAllCost.putExtra("name", trip_name);
            intentAllCost.putExtra("id", trip_id);
            startActivity(intentAllCost);
        });

        backToListCosts.setOnClickListener(v -> {
            Intent intentAllCost = new Intent(ViewCostDetailActivity.this, ViewAllCostActivity.class);
            intentAllCost.putExtra("name", trip_name);
            intentAllCost.putExtra("id", trip_id);
            startActivity(intentAllCost);
        });
    }
}