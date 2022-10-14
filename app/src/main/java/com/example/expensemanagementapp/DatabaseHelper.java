package com.example.expensemanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExpenseManagementApp";
    private static final String TABLE_TRIPS = "trips";
    private static final String TABLE_COSTS = "costs";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String DESTINATION = "destination";
    public static final String NUMBER_OF_TOURISTS = "number_of_tourists";
    public static final String ORGANIZATIONAL_UNIT = "organizational_unit";
    public static final String DESCRIPTION = "description";
    public static final String IS_RISK = "is_risk";

    public static final String TYPE = "type";
    public static final String AMOUNT = "amount";
    public static final String TIME = "time";
    public static final String TRIP_ID = "trip_id";

    private SQLiteDatabase sqLiteDatabase;

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT, " +
                    "   %s BOOLEAN NOT NULL, " +
                    "   %s INTEGER NOT NULL);" +
                    "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s INTEGER NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT, " +
                    "   %s INTEGER NOT NULL, " +
                    "FOREIGN KEY (\"%s\") REFERENCES \"%s\" (\"%s\")",
            TABLE_TRIPS, ID, NAME, DATE, DESTINATION, ORGANIZATIONAL_UNIT, DESCRIPTION, IS_RISK, NUMBER_OF_TOURISTS,
            TABLE_COSTS, ID, TYPE, AMOUNT, TIME, DESCRIPTION, TRIP_ID, TRIP_ID, TABLE_TRIPS, ID);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sqLiteDatabase = getWritableDatabase();
    }

      @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }
}