package edu.gatech.pavyl.pavyl.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.RatData;
import edu.gatech.pavyl.pavyl.model.DataHandler;
import edu.gatech.pavyl.pavyl.model.NetworkUtils;

import static edu.gatech.pavyl.pavyl.model.NetworkUtils.ResponseHandler;

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

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        String dateTime = f.format(date);

        dataMap.put("data_key", "");
        dataMap.put("date", dateTime);
        dataMap.put("location", locationEdit);
        dataMap.put("zip", zipEdit);
        dataMap.put("address", addressEdit);
        dataMap.put("city", cityEdit);
        dataMap.put("borough", boroughEdit);
        dataMap.put("latitude", latitudeEdit);
        dataMap.put("longitude", longitudeEdit);

        RatData sighting = new RatData(dataMap);

        DataHandler.addData(sighting, new ResponseHandler() {
            @Override
            public void handle(NetworkUtils.Response response) {
                if (response.accept) {
                    finish();
                }
            }
        });
    }
}
