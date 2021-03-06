package edu.gatech.pavyl.pavyl.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.SessionData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Determine whether this user is an admin from session data
        boolean admin = (SessionData.extraData.get("admin") != null) &&
                "true".equals(SessionData.extraData.get("admin"));

        //Get necessary UI elements from view
        TextView usernameText = findViewById(R.id.usernameLabel);
        TextView statusText = findViewById(R.id.statusLabel);

        //Update username and status labels based on current session
        usernameText.setText(SessionData.getUsername());
        statusText.setText("Status: " + (admin ? "Admin" : "User"));
    }

    /**
     * Called when the "Logout" button is pressed.
     * @param view - superview
     */
    public void onLogoutPressed(View view) {
        //Return to welcome screen (note we're also popping the login view off the stack)
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //Reset session data
        SessionData.reset();
    }

    /**
     * Called when the "View Rat Data" button is pressed.
     * @param view - superview
     */
    public void onViewDataPressed(View view) {
        Intent intent = new Intent(this, RatDataListActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the "Enter Rat Sighting" button is pressed.
     * @param view - superview
     */
    public void onEnterRatSightingPressed(View view) {
        Intent intent = new Intent(this, EnterRatSightingActivity.class);
        startActivity(intent);
    }

    /**
     * Called when "Map" button is pressed.
     * @param view - superview
     */
    public void onMapPressed(View view) {
        Intent intent = new Intent(this, ChooseMapDataActivity.class);
        startActivity(intent);
    }

    /**
     * Called when "Graph" button is pressed.
     * @param view - superview
     */
    public void onGraphPressed(View view) {
        Intent intent = new Intent(this, ChooseGraphDataActivity.class);
        startActivity(intent);
    }
}
