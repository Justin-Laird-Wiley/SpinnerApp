package com.example.justin.spinnerapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justin.spinnerapp.data.TrainContract;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

//public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String stationString;
    String columnString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.action_bar_spinner);

        final List<Station> stations = new ArrayList<>();

        stations.add(0, new Station("Select a Station", "Fitchburg", null));
        stations.add(new Station("North Station", "Fitchburg", TrainContract.TrainEntry.COLUMN_NORTH_STATION));
        stations.add(new Station("Porter", "Fitchburg", TrainContract.TrainEntry.COLUMN_PORTER));
        stations.add(new Station("Belmont", "Fitchburg", TrainContract.TrainEntry.COLUMN_BELMONT));
        stations.add(new Station("Waverley", "Fitchburg", TrainContract.TrainEntry.COLUMN_WAVERLEY));
        stations.add(new Station("Waltham", "Fitchburg", TrainContract.TrainEntry.COLUMN_WALTHAM));
        stations.add(new Station("Brandeis/Roberts", "Fitchburg", TrainContract.TrainEntry.COLUMN_BRANDEIS_ROBERTS));
        stations.add(new Station("Kendall Green", "Fitchburg", TrainContract.TrainEntry.COLUMN_KENDALL_GREEN));
        stations.add(new Station("Hastings", "Fitchburg", TrainContract.TrainEntry.COLUMN_HASTINGS));
        stations.add(new Station("Silver Hill", "Fitchburg", TrainContract.TrainEntry.COLUMN_SILVER_HILL));
        stations.add(new Station("Lincoln", "Fitchburg", TrainContract.TrainEntry.COLUMN_LINCOLN));
        stations.add(new Station("Concord", "Fitchburg", TrainContract.TrainEntry.COLUMN_CONCORD));
        stations.add(new Station("West Concord", "Fitchburg", TrainContract.TrainEntry.COLUMN_WEST_CONCORD));
        stations.add(new Station("South Acton", "Fitchburg", TrainContract.TrainEntry.COLUMN_SOUTH_ACTON));
        stations.add(new Station("Littleton/Rte 495", "Fitchburg", TrainContract.TrainEntry.COLUMN_LITTLETON_RTE_495));
        stations.add(new Station("Ayer", "Fitchburg", TrainContract.TrainEntry.COLUMN_AYER));
        stations.add(new Station("Shirley", "Fitchburg", TrainContract.TrainEntry.COLUMN_SHIRLEY));
        stations.add(new Station("North Leominster", "Fitchburg", TrainContract.TrainEntry.COLUMN_NORTH_LEOMINSTER));
        stations.add(new Station("Fitchburg", "Fitchburg", TrainContract.TrainEntry.COLUMN_FITCHBURG));
        stations.add(new Station("Wachusett", "Fitchburg", TrainContract.TrainEntry.COLUMN_WACHUSETT));

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stations);

//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString().equals("Select a Station")) {

                } else {
                    stationString = adapterView.getItemAtPosition(i).toString();
                    columnString = stations.get(i).getmDbColumnName();
                    Toast.makeText(adapterView.getContext(), "Selected: " + stationString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //  Android spinner example code
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(this);

    }

//    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//
//        stationString = parent.getItemAtPosition(pos).toString();
//
//    }
//
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

    public void goToAnActivity(View view) {

            Intent intent = new Intent(this, TrainDisplayActivity.class);
            intent.putExtra("SPINNER", stationString);
            intent.putExtra("COLUMN", columnString);

            startActivity(intent);

    }

    public void goEditActivity(View view) {

        Intent intent = new Intent(this, EditTrainActivity.class);
        intent.putExtra("SPINNER", stationString);
        intent.putExtra("COLUMN", columnString);

        startActivity(intent);

    }

}
