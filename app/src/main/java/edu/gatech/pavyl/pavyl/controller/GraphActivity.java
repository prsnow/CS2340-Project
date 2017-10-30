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
import java.util.List;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.RatData;
import edu.gatech.pavyl.pavyl.network.DataHandler;
import edu.gatech.pavyl.pavyl.network.NetworkUtils;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle extras = getIntent().getExtras();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTimeInMillis(extras.getLong("START"));
        end.setTimeInMillis(extras.getLong("END"));

        DataHandler.requestDataInRange(start, end, new NetworkUtils.ResponseHandler() {
            @Override
            public void handle(NetworkUtils.Response response) {
                if (response.accept) {
                    String[] data = response.data;
                    List<RatData> dataList = new ArrayList<>();

                    for (int i = 1; i < data.length; i++) {
                        RatData ret = RatData.parse(data[i]);

                        if(ret != null) {
                            dataList.add(ret);
                        }
                    }

                    GraphView graph = (GraphView) findViewById(R.id.graph);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                    });
                    graph.addSeries(series);
                }
            }
        });
    }
}
