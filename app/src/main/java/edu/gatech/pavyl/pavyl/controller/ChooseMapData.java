package edu.gatech.pavyl.pavyl.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import edu.gatech.pavyl.pavyl.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class ChooseMapData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_map_data);
    }


    /**
     * Called when "Go To Map is pressed."
     * @param view
     */
    public void onGoToMapPressed(View view) {
        String startMonth = ((EditText) findViewById(R.id.start_month)).getText().toString();
        String startDay = ((EditText) findViewById(R.id.start_day)).getText().toString();
        String startYear = ((EditText) findViewById(R.id.start_year)).getText().toString();
        String startString = startMonth + "/" + startDay + "/" + startYear;

        String endMonth = ((EditText) findViewById(R.id.end_month)).getText().toString();
        String endDay = ((EditText) findViewById(R.id.end_day)).getText().toString();
        String endYear = ((EditText) findViewById(R.id.end_year)).getText().toString();
        String endString = endMonth + "/" + endDay + "/" + endYear;

        //DateFormat f = new SimpleDateFormat("MM/dd/yy");

        //Date start;
        //Date end;

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("START", startString);
        intent.putExtra("END", endString);
        startActivity(intent);


    }

}
