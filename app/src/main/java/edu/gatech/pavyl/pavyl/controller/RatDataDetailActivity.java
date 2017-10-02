package edu.gatech.pavyl.pavyl.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.RatData;

public class RatDataDetailActivity extends AppCompatActivity {
    public static final String ARG_DATA_ID = "rat_data_id";

    private RatData ratData;

    private TextView keyLabel;
    private TextView dateLabel;
    private TextView locationLabel;
    private TextView zipLabel;
    private TextView addressLabel;
    private TextView cityLabel;
    private TextView boroughLabel;
    private TextView latitudeLabel;
    private TextView longitudeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_data_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get references to UI elements
        keyLabel = (TextView) findViewById(R.id.keyLabel);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        locationLabel = (TextView) findViewById(R.id.locationLabel);
        zipLabel = (TextView) findViewById(R.id.zipLabel);
        addressLabel = (TextView) findViewById(R.id.addressLabel);
        cityLabel = (TextView) findViewById(R.id.cityLabel);
        boroughLabel = (TextView) findViewById(R.id.boroughLabel);
        latitudeLabel = (TextView) findViewById(R.id.latitudeLabel);
        longitudeLabel = (TextView) findViewById(R.id.longitudeLabel);

        if (getIntent().hasExtra(ARG_DATA_ID)) {
            //Populate the text views with the rat sighting data if supplied with data ID
            HashMap<String, String> dataMap =
                    (HashMap<String, String>) getIntent().getSerializableExtra(ARG_DATA_ID);
            ratData = new RatData(dataMap);

            keyLabel.setText("Key: " + ratData.getData("data_key"));
            dateLabel.setText("Date: " + ratData.getData("date"));
            locationLabel.setText("Location: " + ratData.getData("location"));
            zipLabel.setText("Zip: " + ratData.getData("zip"));
            addressLabel.setText("Address: " + ratData.getData("address"));
            cityLabel.setText("City: " + ratData.getData("city"));
            boroughLabel.setText("Borough: " + ratData.getData("borough"));
            latitudeLabel.setText("Latitude: " + ratData.getData("latitude"));
            longitudeLabel.setText("Longitude: " + ratData.getData("longitude"));
        }
    }

    /**
     * Called when the "Close" button is pressed.
     *
     * @param view - superview
     */
    public void onClosePressed(View view) {
        finish();
    }
}
