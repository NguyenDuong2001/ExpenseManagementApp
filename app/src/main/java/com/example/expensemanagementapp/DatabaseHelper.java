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

    private static final String DATABASE_CREATE_TABLE_TRIPS = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT, " +
                    "   %s BOOLEAN NOT NULL, " +
                    "   %s INTEGER NOT NULL);" ,
            TABLE_TRIPS, ID, NAME, DATE, DESTINATION, ORGANIZATIONAL_UNIT, DESCRIPTION, IS_RISK, NUMBER_OF_TOURISTS);

    private  static final String DATABASE_CREATE_TABLE_COSTS = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s INTEGER NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT, " +
                    "   %s INTEGER NOT NULL, " +
                    "FOREIGN KEY (\"%s\") REFERENCES \"%s\" (\"%s\") ON DELETE CASCADE)",
            TABLE_COSTS, ID, TYPE, AMOUNT, TIME, DESCRIPTION, TRIP_ID, TRIP_ID, TABLE_TRIPS, ID
    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        sqLiteDatabase = getWritableDatabase();
    }

      @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE_TRIPS);
        db.execSQL(DATABASE_CREATE_TABLE_COSTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }
    private static final String TAG = "MyActivity";

    public long insertTrip(TripModelClass tripModelClass) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME, tripModelClass.getName());
        rowValues.put(DATE, tripModelClass.getDate());
        rowValues.put(DESTINATION, tripModelClass.getDestination());
        rowValues.put(ORGANIZATIONAL_UNIT, tripModelClass.getOrganizational_unit());
        rowValues.put(NUMBER_OF_TOURISTS, tripModelClass.getNumber_of_tourists());
        rowValues.put(DESCRIPTION, tripModelClass.getDescription());
        rowValues.put(IS_RISK, tripModelClass.getIs_risk());

        return sqLiteDatabase.insertOrThrow(TABLE_TRIPS, null, rowValues);
    }

    public ArrayList<TripModelClass> getTrips(String search){
        String sql = "select * from " + TABLE_TRIPS;
        if (search != "") {
            sql += " where " + NAME + " like '%" + search + "%'";
        }
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<TripModelClass> trips = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String destination = cursor.getString(3);
            String company = cursor.getString(4);
            String description = cursor.getString(5);
            Boolean is_risk = cursor.getInt(6) == 0;
            Integer tourists = cursor.getInt(7);

            trips.add(new TripModelClass(id, name, description, tourists, company, is_risk, date, destination));
            cursor.moveToNext();
        };

        cursor.close();
        return trips;
    };

    public TripModelClass getTrip(Integer trip_id){
        String sql = "select * from " + TABLE_TRIPS + " where id = " + trip_id;
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        cursor.moveToFirst();

        Integer id = cursor.getInt(0);
        String name = cursor.getString(1);
        String date = cursor.getString(2);
        String destination = cursor.getString(3);
        String company = cursor.getString(4);
        String description = cursor.getString(5);
        Boolean is_risk = cursor.getInt(6) > 0;
        Integer tourists = cursor.getInt(7);

        return new TripModelClass(id, name, description, tourists, company, is_risk, date, destination);
    }

    public void updateTrip(TripModelClass trip){
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME, trip.getName());
        rowValues.put(DATE, trip.getDate());
        rowValues.put(DESTINATION, trip.getDestination());
        rowValues.put(ORGANIZATIONAL_UNIT, trip.getOrganizational_unit());
        rowValues.put(NUMBER_OF_TOURISTS, trip.getNumber_of_tourists());
        rowValues.put(DESCRIPTION, trip.getDescription());
        rowValues.put(IS_RISK, trip.getIs_risk());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_TRIPS, rowValues, ID + "=?", new String[]{trip.getId().toString()});
    }

    public void deleteTrip(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIPS, ID + "=?", new String[]{id.toString()});
    }

    public long insertCost(CostModelClass costModelClass) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(TYPE, costModelClass.getType());
        rowValues.put(AMOUNT, costModelClass.getAmount());
        rowValues.put(TIME, costModelClass.getTime());
        rowValues.put(DESCRIPTION, costModelClass.getDescription());
        rowValues.put(TRIP_ID, costModelClass.getTrip_id());

        return sqLiteDatabase.insertOrThrow(TABLE_COSTS, null, rowValues);
    }

    public ArrayList<CostModelClass> getCosts(Integer trip_id, String search) {
        String sql = "select * from " + TABLE_COSTS + " where " + TRIP_ID + " = " + trip_id;
        if (search != "") {
            sql += " and (" + DESCRIPTION + " like '%" + search + "%'";
            sql += " or " + AMOUNT + " like '%" + search + "%')";
        }

        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<CostModelClass> costs = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Integer id = cursor.getInt(0);
            String type = cursor.getString(1);
            Integer amount = cursor.getInt(2);
            String time = cursor.getString(3);
            String description = cursor.getString(4);

            costs.add(new CostModelClass(id, type, amount, time, description, trip_id));
            cursor.moveToNext();
        }

        cursor.close();
        return costs;
    }

    public CostModelClass getCost (Integer cost_id) {
        String sql = "select * from " + TABLE_COSTS + " where id = " + cost_id;
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        cursor.moveToFirst();

        Integer id = cursor.getInt(0);
        String type = cursor.getString(1);
        Integer amount = cursor.getInt(2);
        String time = cursor.getString(3);
        String description = cursor.getString(4);
        Integer trip_id = cursor.getInt(5);

        return new CostModelClass(id, type, amount, time, description, trip_id);
    }

    public void updateCost(CostModelClass cost){
        ContentValues rowValues = new ContentValues();

        rowValues.put(TYPE, cost.getType());
        rowValues.put(AMOUNT, cost.getAmount());
        rowValues.put(TIME, cost.getTime());
        rowValues.put(DESCRIPTION, cost.getDescription());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_COSTS, rowValues, ID + "=?", new String[]{cost.getId().toString()});
    }

    public void deleteCost(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COSTS, ID + "=?", new String[]{id.toString()});
    }
}