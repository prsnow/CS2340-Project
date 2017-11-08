package edu.gatech.pavyl.pavyl.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.DataHandler;
import edu.gatech.pavyl.pavyl.model.NetworkUtils;
import edu.gatech.pavyl.pavyl.model.SharedData;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // get start and end dates from calling intent
        Bundle extras = getIntent().getExtras();
        final Calendar start = Calendar.getInstance();
        final Calendar end = Calendar.getInstance();
        assert extras != null;

        start.setTimeInMillis(extras.getLong("START"));
        end.setTimeInMillis(extras.getLong("END"));

        // request data between start and end dates
        DataHandler.requestMonthlyChartData(start, end, new NetworkUtils.ResponseHandler() {
            @Override
            public void handle(NetworkUtils.Response response) {
                if (response.getAccept()) { // graph update will run if response is accepted
                    String[] data = response.getData()[1].split(SharedData.DATA_SPLIT);
                    List<DataPoint> dataPoints = new ArrayList<>();

                    try {
                        for (int i = 0; i < data.length; i++) {
                            dataPoints.add(new DataPoint(i + 1, Integer.parseInt(data[i])));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    GraphView graph = findViewById(R.id.graph);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints.toArray(new DataPoint[0]));
                    graph.addSeries(series);
                }
            }
        });
    }
}
