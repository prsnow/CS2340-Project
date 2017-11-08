package edu.gatech.pavyl.pavyl.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.RatData;
import edu.gatech.pavyl.pavyl.model.DataHandler;
import edu.gatech.pavyl.pavyl.model.NetworkUtils;

public class RatDataListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_data_list);

        //Init toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Rat Sightings");

        //Init recycler view
        RecyclerView recyclerView = findViewById(R.id.rat_data_list);
        final RatDataRecyclerViewAdapter adapter = new RatDataRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        //Set up a layout for the recycler view
        final LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        //Set up scroll listener to retrieve new data when necessary
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layout) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount, RecyclerView view) {
                DataHandler.requestData(100, totalItemsCount, new NetworkUtils.ResponseHandler() {
                    @Override
                    public void handle(NetworkUtils.Response response) {
                        if (response.accept) {
                            //If the server accepted our request, start parsing retrieved data
                            List<RatData> toAdd = new ArrayList<>();
                            String[] data = response.data;

                            for (int i = 1; i < data.length; i++) {
                                RatData ret = RatData.parse(data[i]);

                                if (ret != null) {
                                    toAdd.add(ret);
                                }
                            }

                            //add the loaded data to the adapter and notify the view
                            adapter.loadedRatData.addAll(toAdd);
                            adapter.notifyItemRangeInserted(totalItemsCount, toAdd.size());
                        }
                    }
                });
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        //Fetch first round of data
        DataHandler.requestData(100, 0, new NetworkUtils.ResponseHandler() {
            @Override
            public void handle(NetworkUtils.Response response) {
                if (response.accept) {
                    List<RatData> toAdd = new ArrayList<>();
                    String[] data = response.data;

                    for (int i = 1; i < data.length; i++) {
                        if (i < data.length) {
                            RatData ret = RatData.parse(data[i]);

                            if (ret != null) {
                                toAdd.add(ret);
                            }
                        }
                    }

                    adapter.loadedRatData.addAll(toAdd);
                    adapter.notifyItemRangeInserted(0, toAdd.size());
                }
            }
        });
    }

    public class RatDataRecyclerViewAdapter
            extends RecyclerView.Adapter<RatDataRecyclerViewAdapter.ViewHolder> {
        private final List<RatData> loadedRatData = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rat_data_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.ratData = loadedRatData.get(position);

            holder.keyView.setText(holder.ratData.getData("data_key"));
            holder.dateView.setText(holder.ratData.getData("date"));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create a view detailing the selected rat data entry
                    Intent intent = new Intent(v.getContext(), RatDataDetailActivity.class);
                    //Pass along the rat data map in the intent
                    intent.putExtra(RatDataDetailActivity.ARG_DATA_ID, holder.ratData.getDataMap());
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return loadedRatData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View view;
            public final TextView keyView;
            public final TextView dateView;

            public RatData ratData;

            public ViewHolder(View v) {
                super(v);

                view = v;
                keyView = view.findViewById(R.id.key);
                dateView = view.findViewById(R.id.date);
            }
        }
    }
}
