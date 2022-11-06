package com.example.expensemanagementapp;

import androidx.annotation.NonNull;

public class CostModelClass {
    protected Integer id;
    protected String type;
    protected Integer amount;
    protected String description;
    protected String time;
    protected Integer trip_id;

    public CostModelClass(String type, Integer amount, String description, String time, Integer trip_id) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.time = time;
        this.trip_id = trip_id;
    }

    public Integer getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Integer trip_id) {
        this.trip_id = trip_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CostModelClass(Integer id, String type, Integer amount, String time, String description, Integer trip_id) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.time = time;
        this.trip_id = trip_id;
    }

    @NonNull
    @Override
    public String toString() {
        return description +  " - " + amount;
    }
}
