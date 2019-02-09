package com.example.justin.spinnerapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class TrainProvider extends ContentProvider {

    //  String constant for class name for Log entries
    public static final String LOG_TAG = TrainProvider.class.getSimpleName();

    //  Instance variable of Helper object
    TrainDbHelper mDbHelper;

    //  int constant for path pattern to access all rows
    private static final int TRAINS = 100;

    //  int constant for path pattern to access single row
    private static final int TRAIN_ID = 101;

    //  UriMatcher constant used as base pattern for URI matcher
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        //  The calls to addURI() go here, for all of the content URI patterns that the provider
        //  should recognize. All paths added to the UriMatcher have a corresponding code to return
        //  when a match is found.
        sUriMatcher.addURI(TrainContract.CONTENT_AUTHORITY, TrainContract.PATH_TRAINS, TRAINS);
        sUriMatcher.addURI(TrainContract.CONTENT_AUTHORITY, TrainContract.PATH_TRAINS + "/#", TRAIN_ID);
    }

    /**
     * Method used to instantiate new instance of the Helper
     *
     * @return - boolean; always true; ever used?
     */
    @Override
    public boolean onCreate() {
        //  Create new database helper to access database
        mDbHelper = new TrainDbHelper(getContext());
        return true;
    }

    /**
     * Query method that pulls either a single row or the entire database, and puts it in a Cursor
     *
     * @param uri           - URI of database to be read
     * @param projection    - Columns in database to be returned
     * @param selection     - Columns to be searched according to...
     * @param selectionArgs - ...arguments passed in selectionArgs
     * @param sortOrder     - custom sort order if needed
     * @return - Cursor with query data
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        //  Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        //  Cursor to hold query results
        Cursor cursor;

        //  Does the URI passed in match the TRAINS or TRAIN_ID pattern?
        int match = sUriMatcher.match(uri);
        switch (match) {

            //  Search case to retrieve possibly multiple/all rows
            case TRAINS:
                //  Query the database and return results to Cursor
                cursor = database.query(TrainContract.TrainEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            //  Search case to retrieve single row according to _ID passed in; row returned to Cursor
            case TRAIN_ID:
                //  Set up selection to read database according to _ID; pass in the row number to be read in selectionArgs
                selection = TrainContract.TrainEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                //  Query the database and return single row to Cursor
                cursor = database.query(TrainContract.TrainEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            //  If no valid pattern is passed in, throw an exception
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        //  Update the display if anything has changed
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Helper method to set up insertItem
     *
     * @param uri           - URI of database
     * @param contentValues - ContentValues object of values to be inserted in database
     * @return - URI of inserted row
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        Log.i(LOG_TAG, "THIS " + uri);

        switch (match) {
            //  Use TRAINS case since we are inserting row in database, and not looking for single row
            case TRAINS:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertItem(Uri uri, ContentValues values) {

//        //  Do not attempt insert if train...
//        String name = values.getAsString(TrainContract.TrainEntry.COLUMN_TRAIN);
//        if (name == null || name.isEmpty()) {
//            throw new IllegalArgumentException("Item requires a name");
//        }
//
//        //  ...North Station time...
//        String northSation = values.getAsString(TrainContract.TrainEntry.COLUMN_NORTH_STATION);
//        if (northSation == null || northSation.isEmpty()) {
//            throw new IllegalArgumentException("Item requires a name");
//        }
//
//        //  ...Porter time...
//        String porter = values.getAsString(TrainContract.TrainEntry.COLUMN_PORTER);
//        if (porter == null || porter.isEmpty()) {
//            throw new IllegalArgumentException("Item requires a name");
//        }
//
//        //  ...Belmont time...
//        String belmont = values.getAsString(TrainContract.TrainEntry.COLUMN_BELMONT);
//        if (belmont == null || belmont.isEmpty()) {
//            throw new IllegalArgumentException("Item requires a name");
//        }
//
//        //  ...or Waverley time are empty.
//        String waverley = values.getAsString(TrainContract.TrainEntry.COLUMN_WAVERLEY);
//        if (waverley == null || waverley.isEmpty()) {
//            throw new IllegalArgumentException("Item requires a name");
//        }

        //  Some station check to be named later
//        String northSation = values.getAsString(TrainContract.TrainEntry.COLUMN_NORSTA);
//        if (northSation == null || northSation.isEmpty()) {
//            throw new IllegalArgumentException("Item requires a name");
//        }



        //  Get a writable database, and attempt insert; return id of inserted row or -1 if unsuccessful
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(TrainContract.TrainEntry.TABLE_NAME, null, values);

        //  If the insert failed, log the error message and jump out of method
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row " + uri);
            return null;
        }

        //  Update the display to reflect the changes
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Helper method to select single or multiple rows to update and the correct URI to do it
     *
     * @param uri           - URI to update
     * @param contentValues - new values to be put into database
     * @param selection     - columns to be updated
     * @param selectionArgs - update criteria
     * @return - number of rows updated
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TRAINS:
                return updateItem(uri, contentValues, selection, selectionArgs);
            case TRAIN_ID:
                //  For the PET_ID code, extract out the ID from the URI,
                //  so we know which row to update. Selection will be "_id=?" and selection
                //  arguments will be a String array containing the actual ID.
                selection = TrainContract.TrainEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

//        //  Do not attempt update if train...
//        String trainNumber = values.getAsString(TrainContract.TrainEntry.COLUMN_TRAIN);
//        if (trainNumber == null || trainNumber.isEmpty()) {
//            Toast.makeText(getContext(), "invalid input", Toast.LENGTH_SHORT).show();
//            throw new IllegalArgumentException("Item requires a train number");
//        }
//
//        //  ...North Station time...
//        String northStationTime = values.getAsString(TrainContract.TrainEntry.COLUMN_NORTH_STATION);
//        if (northStationTime == null || northStationTime.isEmpty()) {
//            Toast.makeText(getContext(), "invalid input", Toast.LENGTH_SHORT).show();
//            throw new IllegalArgumentException("Item requires a time");
//        }
//
//        //  ...Porter time...
//        String porterTime = values.getAsString(TrainContract.TrainEntry.COLUMN_PORTER);
//        if (porterTime == null || porterTime.isEmpty()) {
//            Toast.makeText(getContext(), "invalid input", Toast.LENGTH_SHORT).show();
//            throw new IllegalArgumentException("Item requires a time");
//        }
//
//        //  ...Belmont time...
//        String belmontTime = values.getAsString(TrainContract.TrainEntry.COLUMN_BELMONT);
//        if (belmontTime == null || belmontTime.isEmpty()) {
//            Toast.makeText(getContext(), "invalid input", Toast.LENGTH_SHORT).show();
//            throw new IllegalArgumentException("Item requires a time");
//        }
//
//        //  ...or Waverley time is empty.
//        String waverleyTime = values.getAsString(TrainContract.TrainEntry.COLUMN_WAVERLEY);
//        if (waverleyTime == null || waverleyTime.isEmpty()) {
//            Toast.makeText(getContext(), "invalid input", Toast.LENGTH_SHORT).show();
//            throw new IllegalArgumentException("Item requires a time");
//        }

        //  ...price...
//        Integer price = values.getAsInteger(TrainContract.TrainEntry.COLUMN_PRICE);
//        if (price != null && price < 0) {
//            throw new IllegalArgumentException("Price cannot be negative");
//        }
//
        //  ...or quantity field is empty
//        Integer quantity = values.getAsInteger(TrainContract.TrainEntry.COLUMN_QUANTITY);
//        if (quantity != null && quantity < 0) {
//            throw new IllegalArgumentException("Quantity cannot be negative");
//        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        //  Get the writable database, and attempt update; return number of rows updated
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(TrainContract.TrainEntry.TABLE_NAME, values, selection, selectionArgs);

        //  If rows were updated, update display to reflect changes
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * Method to delete rows from database; TRAINS deletes all rows; TRAIN_ID deletes one row
     *
     * @param uri           - URI of database
     * @param selection     - column (ID) used to specify row to delete
     * @param selectionArgs - selection argument that is row to be deleted
     * @return - int of number of rows deleted
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TRAINS:
                //  Delete all rows that match the selection and selection args
                int rowsDeleted = database.delete(TrainContract.TrainEntry.TABLE_NAME, selection, selectionArgs);

                //  If a row has been deleted, update the display
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;

            case TRAIN_ID:
                //  Delete a single row given by the ID in the URI
                selection = TrainContract.TrainEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                //  Delete the row at the specified ID:  content://com.example.justin.Trainappone/warehouse
                int rowDeleted = database.delete(TrainContract.TrainEntry.TABLE_NAME, selection, selectionArgs);

                //  If a row has been deleted, update the display
                if (rowDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowDeleted;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TRAINS:
                return TrainContract.TrainEntry.CONTENT_LIST_TYPE;
            case TRAIN_ID:
                return TrainContract.TrainEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
