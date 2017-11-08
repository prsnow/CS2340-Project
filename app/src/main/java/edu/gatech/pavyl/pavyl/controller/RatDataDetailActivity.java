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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_data_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get references to UI elements
        TextView keyLabel = findViewById(R.id.keyLabel);
        TextView dateLabel = findViewById(R.id.dateLabel);
        TextView locationLabel = findViewById(R.id.locationLabel);
        TextView zipLabel = findViewById(R.id.zipLabel);
        TextView addressLabel = findViewById(R.id.addressLabel);
        TextView cityLabel = findViewById(R.id.cityLabel);
        TextView boroughLabel = findViewById(R.id.boroughLabel);
        TextView latitudeLabel = findViewById(R.id.latitudeLabel);
        TextView longitudeLabel = findViewById(R.id.longitudeLabel);

        if (getIntent().hasExtra(ARG_DATA_ID)) {
            //Populate the text views with the rat sighting data if supplied with data ID
            @SuppressWarnings("unchecked")
            HashMap<String, String> dataMap =
                    (HashMap<String, String>) getIntent().getSerializableExtra(ARG_DATA_ID);
            RatData ratData = new RatData(dataMap);

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
