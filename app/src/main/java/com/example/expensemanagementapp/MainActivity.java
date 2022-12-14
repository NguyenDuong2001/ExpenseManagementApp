package com.example.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.textName);
        EditText description = findViewById(R.id.textDescription);
        EditText company = findViewById(R.id.textOrganizational);
        EditText tourists = findViewById(R.id.textNumber);
        EditText destination = findViewById(R.id.textDestination);
        EditText date = findViewById(R.id.textDate);
        Switch risk = findViewById(R.id.switch1);

        Button add = findViewById(R.id.buttonAdd);
        Button viewAll = findViewById(R.id.buttonViewAll);

        add.setOnClickListener(e -> {
            if(name.getText().toString().matches("")){
                Toast.makeText(this, "Field name is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(tourists.getText().toString().matches("")){
                Toast.makeText(this, "Field number of tourists from is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(destination.getText().toString().matches("")){
                Toast.makeText(this, "Field destination to is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(date.getText().toString().matches("")){
                Toast.makeText(this, "Field date is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseHelper db = new DatabaseHelper(this);
            TripModelClass trip = new TripModelClass(
                    name.getText().toString(),
                    description.getText().toString(),
                    Integer.parseInt(tourists.getText().toString()),
                    company.getText().toString(),
                    risk.isChecked(),
                    date.getText().toString(),
                    destination.getText().toString()
            );
            db.insertTrip(trip);

            Toast.makeText(this, "Add successfully!", Toast.LENGTH_SHORT).show();

            name.setText("");
            description.setText("");
            tourists.setText("");
            company.setText("");
            date.setText("");
            destination.setText("");
            risk.setChecked(false);
        });

        viewAll.setOnClickListener(e ->{
            Intent intent = new Intent(MainActivity.this, ViewAllTripActivity.class);
            startActivity(intent);
        });
    }
}