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
        DataHandler.requestDataInRange(start, end, new NetworkUtils.ResponseHandler() {
            @Override
            public void handle(NetworkUtils.Response response) {
                if (response.accept) { // graph update will run if response is accepted
                    String[] data = response.data;
                    Map<Integer, Integer> dataPerMonth = new HashMap<>();

                    //go through all data loaded from server
                    for (int i = 1; i < data.length; i++) {
                        RatData ret = RatData.parse(data[i]);
                        if(ret != null) { // make sure data can be parsed
                            Calendar date = ret.getDate();

                            if(date != null) { // make sure date is formatted correctly in data
                                int month = date.get(Calendar.YEAR)*12 + (date.get(Calendar.MONTH)+1);
                                // update dataPerMonth map
                                if(dataPerMonth.get(month) == null) {
                                    dataPerMonth.put(month, 1);
                                }
                                else {
                                    dataPerMonth.put(month, dataPerMonth.get(month)+1);
                                }
                            }
                        }
                    }

                    int startMonth = start.get(Calendar.YEAR)*12 + (start.get(Calendar.MONTH)+1);
                    List<DataPoint> dataPoints = new ArrayList<>();

                    for(Map.Entry<Integer, Integer> entry : dataPerMonth.entrySet()) {
                        dataPoints.add(new DataPoint((entry.getKey()-startMonth+1), entry.getValue()));
                    }

                    GraphView graph = (GraphView) findViewById(R.id.graph);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints.toArray(new DataPoint[0]));
                    graph.addSeries(series);
                }
            }
        });
    }
}
