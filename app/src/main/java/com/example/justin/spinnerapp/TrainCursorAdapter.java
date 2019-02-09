package com.example.justin.spinnerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.justin.spinnerapp.data.TrainContract;

public class TrainCursorAdapter extends CursorAdapter {

    String passedInStationName;

    /**
     * Constructs a new {@link TrainCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public TrainCursorAdapter(Context context, Cursor c, String column) {
        super(context, c,  0);
        passedInStationName = column;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        //  Find individual views that we want to modify in the list item layout
        TextView trainTextView = (TextView) view.findViewById(R.id.train);
        TextView timeTextView = (TextView) view.findViewById(R.id.time);
//        TextView columnTextView = (TextView) view.findViewById(R.id.column);
        
        //  Find the columns of pet attributes that we're interested in; grabbing all the columns to pass them into the Button.setTag
        final int idColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry._ID);
        final int trainColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_TRAIN);
        final int timeColumnIndex = cursor.getColumnIndex(passedInStationName);

        //  Read the item attributes from the Cursor for the current item
        int itemId = cursor.getInt(idColumnIndex);
        int trainNumber = cursor.getInt(trainColumnIndex);
        int stationArrivalTime = cursor.getInt(timeColumnIndex);

        //  Ready a ContentValues object for data for insert
//        ContentValues tagValues = new ContentValues();

        //  Set the values
//        tagValues.put(TrainContract.TrainEntry.COLUMN_ITEM, itemName);
//        tagValues.put(TrainContract.TrainEntry.COLUMN_PRICE, price);
//        tagValues.put(TrainContract.TrainEntry.COLUMN_QUANTITY, itemQuantity);
//        tagValues.put(TrainContract.TrainEntry.COLUMN_SUPPLIER, supplier);
//        tagValues.put(TrainContract.TrainEntry.COLUMN_SUPPLIER_PHONE_NO, supplierPhone);

        //  Put the ID and the values into tags for the Button
//        sellButton.setTag(R.string.key_one, itemId);
//        sellButton.setTag(R.string.key_two, tagValues);

        //  Format station time
        String stationTimeString = String.format("%02d:%02d", stationArrivalTime / 100, stationArrivalTime % 100);

        // Populate fields with extracted properties
        trainTextView.setText(Integer.toString(trainNumber));
        timeTextView.setText(stationTimeString);

    }

}
