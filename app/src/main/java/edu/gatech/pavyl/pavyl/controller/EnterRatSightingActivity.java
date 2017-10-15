package edu.gatech.pavyl.pavyl.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;
import java.util.Date;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.RatData;

import android.widget.EditText;

public class EnterRatSightingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_rat_sighting);
    }

    /**
     * Called when the "Cancel" button is pressed.
     * @param view - superview
     */
    public void onCancelPressed(View view) {
        finish();
    }

    /**
     * Called when "Add Sighting" button is pressed.
     * Adds rat sighting to database.
     * Duplicates: ???
     *
     * @param view - superview
     */
    public void onAddSighting(View view) {
        HashMap<String, String> dataMap = new HashMap<>();

        String addressEdit = ((EditText) findViewById(R.id.address)).getText().toString();
        String locationEdit = ((EditText) findViewById(R.id.location)).getText().toString();
        String zipEdit = ((EditText) findViewById(R.id.zip)).getText().toString();
        String cityEdit = ((EditText) findViewById(R.id.city)).getText().toString();
        String boroughEdit = ((EditText) findViewById(R.id.borough)).getText().toString();
        String latitudeEdit = ((EditText) findViewById(R.id.latitude)).getText().toString();
        String longitudeEdit = ((EditText) findViewById(R.id.longitude)).getText().toString();

        int key = makeKey();
        Date date = new Date();

        dataMap.put("data_key", "" + key);
        dataMap.put("date", date.toString());
        dataMap.put("location", locationEdit);
        dataMap.put("zip", zipEdit);
        dataMap.put("address", addressEdit);
        dataMap.put("city", cityEdit);
        dataMap.put("borough", boroughEdit);
        dataMap.put("latitude", latitudeEdit);
        dataMap.put("longitude", longitudeEdit);

        RatData sighting = new RatData(dataMap);


    }

    /**
     * Makes unique key
     *
     * @return key
     */
    private int makeKey() {
        //find largest key in current + 1?
        return 5;
    }
}
