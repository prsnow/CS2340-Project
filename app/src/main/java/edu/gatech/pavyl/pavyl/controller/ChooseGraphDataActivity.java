package edu.gatech.pavyl.pavyl.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.gatech.pavyl.pavyl.R;

public class ChooseGraphDataActivity extends AppCompatActivity {
    private static final SimpleDateFormat FMT = new SimpleDateFormat("MM/dd/yy");
    private Calendar startDate;
    private Calendar endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_graph_data);
    }

    /**
     * Called when start date select button is pressed.
     * @param view current view
     */
    public void onStartDateSelect(View view) {
        final TextView text = findViewById(R.id.fromDateLabelGraph);
        Calendar date = startDate;

        if(date == null)
        {
            date = Calendar.getInstance();
        }

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day)
            {
                startDate = Calendar.getInstance();
                startDate.set(Calendar.YEAR, year);
                startDate.set(Calendar.MONTH, month);
                startDate.set(Calendar.DAY_OF_MONTH, day);
                text.setText("From Date: " + FMT.format(startDate.getTime()));
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    /**
     * Called when start date select button is pressed.
     * @param view current view
     */
    public void onEndDateSelect(View view) {
        final TextView text = findViewById(R.id.toDateLabelGraph);
        Calendar date = endDate;

        if(date == null)
        {
            date = Calendar.getInstance();
        }

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day)
            {
                endDate = Calendar.getInstance();
                endDate.set(Calendar.YEAR, year);
                endDate.set(Calendar.MONTH, month);
                endDate.set(Calendar.DAY_OF_MONTH, day);
                text.setText("To Date: " + FMT.format(endDate.getTime()));
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    /**
     * Called when "Go To Graph" is pressed.
     * @param view current view
     */
    public void onGoToGraphPressed(View view) {
        if((startDate != null) && (endDate != null)) {
            Intent intent = new Intent(this, GraphActivity.class);
            intent.putExtra("START", startDate.getTimeInMillis());
            intent.putExtra("END", endDate.getTimeInMillis());
            startActivity(intent);
        }
    }
}
