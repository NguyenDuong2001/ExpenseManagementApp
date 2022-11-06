package com.example.expensemanagementapp;

import androidx.annotation.NonNull;

public class TripModelClass {
    public TripModelClass(String name, String description, Integer number_of_tourists, String organizational_unit, Boolean is_risk, String date, String destination) {
        this.name = name;
        this.description = description;
        this.number_of_tourists = number_of_tourists;
        this.organizational_unit = organizational_unit;
        this.is_risk = is_risk;
        this.date = date;
        this.destination = destination;
    }

    public TripModelClass(
            Integer id,
            String name,
            String description,
            Integer number_of_tourists,
            String organizational_unit,
            Boolean is_risk,
            String date,
            String destination
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number_of_tourists = number_of_tourists;
        this.organizational_unit = organizational_unit;
        this.is_risk = is_risk;
        this.date = date;
        this.destination = destination;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumber_of_tourists() {
        return number_of_tourists;
    }

    public void setNumber_of_tourists(Integer number_of_tourists) {
        this.number_of_tourists = number_of_tourists;
    }

    public String getOrganizational_unit() {
        return organizational_unit;
    }

    public void setOrganizational_unit(String organizational_unit) {
        this.organizational_unit = organizational_unit;
    }

    public Boolean getIs_risk() {
        return is_risk;
    }

    public void setIs_risk(Boolean is_risk) {
        this.is_risk = is_risk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    protected Integer id;
    protected String name;
    protected String description;
    protected Integer number_of_tourists;
    protected String organizational_unit;
    protected Boolean is_risk;
    protected String date;
    protected String destination;

    @NonNull
    @Override
    public String toString() {
        return name +  " - " + date;
    }
}
