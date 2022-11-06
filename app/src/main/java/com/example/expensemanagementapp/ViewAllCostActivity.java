package com.example.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ViewAllCostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cost);

        Intent intent = getIntent();
        Integer trip_id = intent.getIntExtra("id", 0);
        String trip_name = intent.getStringExtra("name");
        DatabaseHelper db = new DatabaseHelper(this);

        List<CostModelClass> costs = db.getCosts(trip_id, "");
        ArrayAdapter<CostModelClass> arrayAdapter
                = new ArrayAdapter<CostModelClass>(this, android.R.layout.simple_list_item_1 , costs);
        ListView listCosts = findViewById(R.id.listCosts);
        listCosts.setAdapter(arrayAdapter);

        TextView nameTrip = findViewById(R.id.textTripName);
        nameTrip.setText(trip_name);

        Button buttonAddCost = findViewById(R.id.buttonAddCost);
        buttonAddCost.setOnClickListener(e -> {
            Intent intentAddCost = new Intent(ViewAllCostActivity.this, CreateCostActivity.class);
            intentAddCost.putExtra("name", trip_name);
            intentAddCost.putExtra("id", trip_id);
            startActivity(intentAddCost);
        });

        View backToTripDetail = findViewById(R.id.backToTripDetail);
        backToTripDetail.setOnClickListener(e -> {
            Intent intentAllTrip = new Intent(ViewAllCostActivity.this, ViewTripDetailActivity.class);
            intentAllTrip.putExtra("id", trip_id);
            startActivity(intentAllTrip);
        });

        EditText search = findViewById(R.id.textSearchCosts);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                List<CostModelClass> costs = db.getCosts(trip_id, s.toString().trim());
                ArrayAdapter<CostModelClass> arrayAdapter
                        = new ArrayAdapter<CostModelClass>(ViewAllCostActivity.this, android.R.layout.simple_list_item_1 , costs);
                ListView listCosts = findViewById(R.id.listCosts);
                listCosts.setAdapter(arrayAdapter);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        listCosts.setOnItemClickListener((adapterView, view1, i, l) -> {
            Intent intentDetailCots = new Intent(ViewAllCostActivity.this, ViewCostDetailActivity.class);
            intentDetailCots.putExtra("id", costs.get(i).getId());
            intentDetailCots.putExtra("trip_id", trip_id);
            intentDetailCots.putExtra("trip_name", trip_name);
            startActivity(intentDetailCots);
        });
    }
}