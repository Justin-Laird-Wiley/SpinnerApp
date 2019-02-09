package com.example.justin.spinnerapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justin.spinnerapp.data.TrainContract;

public class TrainDisplayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    TrainCursorAdapter mAdapter;
    private static final int LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_item);
        setContentView(R.layout.activity_train_display);


        Bundle extras = getIntent().getExtras();
        String columnString = extras.getString("COLUMN");


        //  ListView to display inventory
        ListView inventoryItems = (ListView) findViewById(R.id.list);

        //  Empty view stuff to be activated
        View emptyView = findViewById(R.id.empty_view);
        inventoryItems.setEmptyView(emptyView);

        //  Create the Adapter...
        mAdapter = new TrainCursorAdapter(this, null, columnString);

        //  ...and attach it to the ListView
        inventoryItems.setAdapter(mAdapter);

        //  Set up the onItemListener to go to the EditorActivity for a single item
        inventoryItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //  Create the Intent, pass in the URI, and set it running
                Intent intent = new Intent(TrainDisplayActivity.this, EditTrainActivity.class);
                intent.setData(Uri.withAppendedPath(TrainContract.TrainEntry.CONTENT_URI, Long.toString(id)));
                startActivity(intent);
            }
        });

        //  Start the Loader to populate the ListView
        getLoaderManager().initLoader(LOADER_ID, null, this);


//        String trainString;
//        String columnString;
//
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                trainString = null;
//                columnString = null;
//            } else {
//                trainString = extras.getString("SPINNER");
//                columnString = extras.getString("COLUMN");
//            }
//        } else {
//            trainString = (String) savedInstanceState.getSerializable("STRING_I_NEED");
//            columnString = (String) savedInstanceState.getSerializable("STRING_I_NEED");
//        }
//
//        TextView trainDisplayView = (TextView) findViewById(R.id.train);
//        TextView columnDisplayView = (TextView) findViewById(R.id.column);
//
//        trainDisplayView.setText(trainString);
//        columnDisplayView.setText(columnString);

    }


    /**
     * Mandatory Loader method used to query the database and return a Cursor
     *
     * @param id   - row to be query
     * @param args - query args if needed
     * @return - Cursor with data
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //  Create new CursorLoader with null fields to grab all columns
        return new CursorLoader(this,
                TrainContract.TrainEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    /**
     * Mandatory Loader method that swaps current Cursor for new Cursor from Loader
     *
     * @param loader - Loader from onCreateLoader
     * @param data   - Cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    /**
     * Mandatory Loader method that swaps an empty Cursor for the current Cursor on reset
     *
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
