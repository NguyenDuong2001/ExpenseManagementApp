package com.example.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class ViewTripDetailActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip_detail);

        Intent intent = getIntent();
        Integer trip_id = intent.getIntExtra("id", 0);
        DatabaseHelper db = new DatabaseHelper(this);

        TripModelClass trip = db.getTrip(trip_id);

        EditText name = findViewById(R.id.textDetailName);
        EditText description = findViewById(R.id.textDetailDescription);
        EditText company = findViewById(R.id.textDetailOrganizational);
        EditText tourists = findViewById(R.id.textDetailNumber);
        EditText destination = findViewById(R.id.textDetailDestination);
        EditText date = findViewById(R.id.textDetailDate);
        Switch risk = findViewById(R.id.switchDetailRisk);
        Button update = findViewById(R.id.buttonUpdate);
        Button delete = findViewById(R.id.buttonDelete);
        ImageView back = findViewById(R.id.imageBack);
        Button viewCosts = findViewById(R.id.buttonViewAllCots);


        name.setText(trip.getName());
        description.setText(trip.getDescription());
        tourists.setText(trip.getNumber_of_tourists().toString());
        company.setText(trip.getOrganizational_unit());
        date.setText(trip.getDate());
        destination.setText(trip.getDestination());
        risk.setChecked(trip.getIs_risk());

        back.setOnClickListener(v -> {
            Intent intentAllTrip = new Intent(ViewTripDetailActivity.this, ViewAllTripActivity.class);
            startActivity(intentAllTrip);
        });

        update.setOnClickListener(v -> {
            trip.setName(name.getText().toString());
            trip.setDescription(description.getText().toString());
            trip.setNumber_of_tourists(Integer.parseInt(tourists.getText().toString()));
            trip.setOrganizational_unit(company.getText().toString());
            trip.setDestination(destination.getText().toString());
            trip.setDate(date.getText().toString());
            trip.setIs_risk(risk.isChecked());

            db.updateTrip(trip);
        });

        delete.setOnClickListener(v -> {
            db.deleteTrip(trip_id);

            Intent intentAllTrip = new Intent(ViewTripDetailActivity.this, ViewAllTripActivity.class);
            startActivity(intentAllTrip);
        });

        viewCosts.setOnClickListener(v -> {
            Intent intentAllCost = new Intent(ViewTripDetailActivity.this, ViewAllCostActivity.class);
            intentAllCost.putExtra("name", trip.getName());
            intentAllCost.putExtra("id", trip.getId());
            startActivity(intentAllCost);
        });
    }
}