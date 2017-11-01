package edu.gatech.pavyl.pavyl.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.RatData;
import edu.gatech.pavyl.pavyl.network.DataHandler;
import edu.gatech.pavyl.pavyl.network.NetworkUtils;
import edu.gatech.pavyl.pavyl.network.SharedData;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // get start and end dates from calling intent
        Bundle extras = getIntent().getExtras();
        final Calendar start = Calendar.getInstance();
        final Calendar end = Calendar.getInstance();
        start.setTimeInMillis(extras.getLong("START"));
        end.setTimeInMillis(extras.getLong("END"));

        // request data between start and end dates
        DataHandler.requestMonthlyChartData(start, end, new NetworkUtils.ResponseHandler() {
            @Override
            public void handle(NetworkUtils.Response response) {
                if (response.accept) { // graph update will run if response is accepted
                    String[] data = response.data[1].split(SharedData.DATA_SPLIT);
                    List<DataPoint> dataPoints = new ArrayList<>();

                    try {
                        for (int i = 0; i < data.length; i++) {
                            dataPoints.add(new DataPoint(i+1, Integer.parseInt(data[i])));
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    GraphView graph = (GraphView) findViewById(R.id.graph);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints.toArray(new DataPoint[0]));
                    graph.addSeries(series);
                }
            }
        });
    }
}
