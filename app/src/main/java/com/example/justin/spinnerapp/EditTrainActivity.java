package com.example.justin.spinnerapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.justin.spinnerapp.data.TrainContract;


//public class EditTrainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {


    public class EditTrainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


        //  Class name constant for Log entries
    private static final String LOG_TAG = EditTrainActivity.class.getSimpleName();

    //  ID constant for loader
    private static final int EXISTING_LOADER = 1;

    //  EditText fields
    private EditText mTrainEditText;
    private EditText mNorthStationEditText;
    private EditText mPorterEditText;
    private EditText mBelmontEditText;
    private EditText mWaverleyEditText;
    private EditText mWalthamEditText;
    private EditText mBrandeisRobertsEditText;
    private EditText mKendallGreenEditText;
    private EditText mHastingsEditText;
    private EditText mSilverHillEditText;
    private EditText mLincolnEditText;
    private EditText mConcordEditText;
    private EditText mWestConcordEditText;
    private EditText mSouthActonEditText;
    private EditText mLittletonRte495EditText;
    private EditText mAyerEditText;
    private EditText mShirleyEditText;
    private EditText mNorthLeominsterEditText;
    private EditText mFitchburgEditText;
    private EditText mWachusettEditText;
    
    //  URI of the current item being edited
    private Uri mCurrentItemUri;

    //  Boolean set to true if any field in item has been changed
    private boolean mItemHasChanged = false;
    
    //  Boolean; true if all input valid
    public boolean inputIsValid = false;

    Cursor goodCursor = null;

    //  Create onTouchListener for screen changes
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

    /**
     * Method that receives Intent from InventoryActivity; attaches all views in edit display;
     * sets up onTouchListener's for each EditText views
     *
     * @param savedInstanceState - any parameters passed in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_train);

        //  Get the Intent from InventoryActivity; pull out the URI of the item
        Intent intent = getIntent();
        mCurrentItemUri = intent.getData();

        //  If there is no URI, create and insert a new item record
        if (mCurrentItemUri == null) {
            setTitle("Add an Item");
            invalidateOptionsMenu();
            Toast.makeText(this, "Please fill all data fields", Toast.LENGTH_SHORT).show();
            //  ...else initialize the loader that will pull up the item row to be edited
        } else {
            setTitle("Edit an Item");
            getSupportLoaderManager().initLoader(EXISTING_LOADER, null, this);

//            getLoaderManager().initLoader(EXISTING_LOADER, null, this);

        }

        //  Attach all the relevant TextView's in the editor layout
        mTrainEditText = (EditText) findViewById(R.id.edit_train);
        mNorthStationEditText = (EditText) findViewById(R.id.edit_north_station);
        mPorterEditText = (EditText) findViewById(R.id.edit_porter);
        mBelmontEditText = (EditText) findViewById(R.id.edit_belmont);
        mWaverleyEditText = (EditText) findViewById(R.id.edit_waverley);
        mWalthamEditText = (EditText) findViewById(R.id.edit_waltham); 
        mBrandeisRobertsEditText = (EditText) findViewById(R.id.edit_brandeis_roberts);
        mKendallGreenEditText = (EditText) findViewById(R.id.edit_kendall_green);
        mHastingsEditText = (EditText) findViewById(R.id.edit_hastings);
        mSilverHillEditText = (EditText) findViewById(R.id.edit_silver_hill);
        mLincolnEditText = (EditText) findViewById(R.id.edit_lincoln);
        mConcordEditText = (EditText) findViewById(R.id.edit_concord);
        mWestConcordEditText = (EditText) findViewById(R.id.edit_west_concord);
        mSouthActonEditText = (EditText) findViewById(R.id.edit_south_acton);
        mLittletonRte495EditText = (EditText) findViewById(R.id.edit_littleton_rte_495);
        mAyerEditText = (EditText) findViewById(R.id.edit_ayer);
        mShirleyEditText = (EditText) findViewById(R.id.edit_shirley);
        mNorthLeominsterEditText = (EditText) findViewById(R.id.edit_north_leominster);
        mFitchburgEditText = (EditText) findViewById(R.id.edit_fitchburg);
        mWachusettEditText = (EditText) findViewById(R.id.edit_wachusett);


        //  Set up the onTouchListener's for each EditText view
        mTrainEditText.setOnTouchListener(mTouchListener);
        mNorthStationEditText.setOnTouchListener(mTouchListener);
        mPorterEditText.setOnTouchListener(mTouchListener);
        mBelmontEditText.setOnTouchListener(mTouchListener);
        mWaverleyEditText.setOnTouchListener(mTouchListener);

    }

    /**
     * Method to insert item into database
     */
    private void saveItem() {
        //  New URI of newly inserted item record into database
        Uri newUri;

        //  Pull the input data from the editor view
        String trainNumberString = mTrainEditText.getText().toString().trim();
        String northStationString = mNorthStationEditText.getText().toString().trim();
        String porterString = mPorterEditText.getText().toString().trim();
        String belmontString = mBelmontEditText.getText().toString().trim();
        String waverleyString = mWaverleyEditText.getText().toString().trim();

        String walthamString = mWalthamEditText.getText().toString().trim();
        String brandeisRobertsString = mBrandeisRobertsEditText.getText().toString().trim();
        String kendallGreenString = mKendallGreenEditText.getText().toString().trim();
        String hastingsString = mHastingsEditText.getText().toString().trim();
        String silverHillString = mSilverHillEditText.getText().toString().trim();
        String lincolnString = mLincolnEditText.getText().toString().trim();
        String concordString = mConcordEditText.getText().toString().trim();
        String westConcordString = mWestConcordEditText.getText().toString().trim();
        String southActonString = mSouthActonEditText.getText().toString().trim();
        String littletonRte495String = mLittletonRte495EditText.getText().toString().trim();
        String ayerString = mAyerEditText.getText().toString().trim();
        String shirleyString = mShirleyEditText.getText().toString().trim();
        String northLeominsterString = mNorthLeominsterEditText.getText().toString().trim();
        String fitchburgString = mFitchburgEditText.getText().toString().trim();
        String wachusettString = mWachusettEditText.getText().toString().trim();






//        //  If all the fields are empty, make a Toast, set inputIsValid = false, and jump out
//        if (TextUtils.isEmpty(trainNumberString) &&
//                TextUtils.isEmpty(northStationString) &&
//                TextUtils.isEmpty(porterString) &&
//                TextUtils.isEmpty(belmontString) &&
//                TextUtils.isEmpty(waverleyString)
//                ) {
//
//            inputIsValid = false;
//            Toast.makeText(this, "All data fields are empty -- fill them in", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //  If trainNumberString is empty, make a Toast, set inputIsValid = false, and jump out
//        if (TextUtils.isEmpty(trainNumberString) || trainNumberString == null) {
//            inputIsValid = false;
//            Toast.makeText(this, "Please enter valid input in item field", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //  If northStationString is empty, make a Toast, set inputIsValid = false, and jump out
//        if (TextUtils.isEmpty(northStationString) || northStationString == null) {
//            inputIsValid = false;
//            Toast.makeText(this, "Please enter valid input in North Station field", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //  If porterString is empty, make a Toast, set inputIsValid = false, and jump out
//        if (TextUtils.isEmpty(porterString) || porterString == null) {
//            inputIsValid = false;
//            Toast.makeText(this, "Please enter valid input in price field", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //  If belmontString is empty, make a Toast, set inputIsValid = false, and jump out
//        if (TextUtils.isEmpty(belmontString) || belmontString == null) {
//            inputIsValid = false;
//            Toast.makeText(this, "Please enter valid input in price field", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //  If waverleyString is empty, make a Toast, set inputIsValid = false, and jump out
//        if (TextUtils.isEmpty(waverleyString) || waverleyString == null) {
//            inputIsValid = false;
//            Toast.makeText(this, "Please enter valid input in price field", Toast.LENGTH_SHORT).show();
//            return;
//        }




        //  If we clear all the input checks, set inputIsValid to true
        inputIsValid = true;

        //  Convert the String's to int's as needed
//        int priceInt = Integer.parseInt(priceString);
//        int quantityInt = Integer.parseInt(quantityString);

        //  Format the phone number field
//        String formattedSupplierPhoneNumber = PhoneNumberUtils.formatNumber(supplierPhoneNoString);

        //  Ready a ContentValues object for data for insert
        ContentValues values = new ContentValues();

        //  Set the values
        values.put(TrainContract.TrainEntry.COLUMN_TRAIN, trainNumberString);
        values.put(TrainContract.TrainEntry.COLUMN_NORTH_STATION, northStationString);
        values.put(TrainContract.TrainEntry.COLUMN_PORTER, porterString);
        values.put(TrainContract.TrainEntry.COLUMN_BELMONT, belmontString);
        values.put(TrainContract.TrainEntry.COLUMN_WAVERLEY, waverleyString);

        values.put(TrainContract.TrainEntry.COLUMN_WALTHAM, walthamString);
        values.put(TrainContract.TrainEntry.COLUMN_BRANDEIS_ROBERTS, brandeisRobertsString);
        values.put(TrainContract.TrainEntry.COLUMN_KENDALL_GREEN, kendallGreenString);
        values.put(TrainContract.TrainEntry.COLUMN_HASTINGS, hastingsString);
        values.put(TrainContract.TrainEntry.COLUMN_SILVER_HILL, silverHillString);

        values.put(TrainContract.TrainEntry.COLUMN_LINCOLN, lincolnString);
        values.put(TrainContract.TrainEntry.COLUMN_CONCORD, concordString);
        values.put(TrainContract.TrainEntry.COLUMN_WEST_CONCORD, westConcordString);
        values.put(TrainContract.TrainEntry.COLUMN_SOUTH_ACTON, southActonString);
        values.put(TrainContract.TrainEntry.COLUMN_LITTLETON_RTE_495, littletonRte495String);

        values.put(TrainContract.TrainEntry.COLUMN_AYER, ayerString);
        values.put(TrainContract.TrainEntry.COLUMN_SHIRLEY, shirleyString);
        values.put(TrainContract.TrainEntry.COLUMN_NORTH_LEOMINSTER, northLeominsterString);
        values.put(TrainContract.TrainEntry.COLUMN_FITCHBURG, fitchburgString);
        values.put(TrainContract.TrainEntry.COLUMN_WACHUSETT, wachusettString);

        if (mCurrentItemUri == null) {
            newUri = getContentResolver().insert(TrainContract.TrainEntry.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, "Insert failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insert success", Toast.LENGTH_SHORT).show();
            }
        } else {
            String[] selectionArgs = new String[]{String.valueOf(ContentUris.parseId(mCurrentItemUri))};

            int row = getContentResolver().update(mCurrentItemUri,
                    values,
                    TrainContract.TrainEntry._ID,
                    selectionArgs);

            if (row == -1) {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update succeeded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Method to delete single item from database
     */
    private void deleteItem() {
        //  Delete the row at mCurrentUri; return number of rows deleted
        int rowDeleted = getContentResolver().delete(mCurrentItemUri,
                null, null);

        //  Display Toast message
        if (rowDeleted == 0) {
            Toast.makeText(this, "No item deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
        }

        //  Close EditScheduleActivity and return to InventoryActivity
        finish();
    }

    /**
     * Method to set up the EditScheduleActivity menu in the Action Bar
     *
     * @param menu - the menu to be displayed
     * @return - return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_edit_train, menu);
        return true;
    }

    /**
     * Method to register selected menu item
     *
     * @param item - menu item to act upon
     * @return - return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            //  Use check in Action Bar to save/update current item record
            case R.id.action_save:
                //  Attempt to save current data in TextEdit views
                saveItem();
                //  If all entered data is valid, then exit EditScheduleActivity and return to InventoryActivity
                if (inputIsValid) {
                    finish();
                }
                return true;

            //  Use delete option in Action Bar menu to delete current record
            case R.id.action_delete:
                //  If URI is not null, delete the item
                if (mCurrentItemUri != null) {
                    showDeleteConfirmationDialog();
                }
                return true;

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditTrainActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditTrainActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to block delete option when inserting a new item
     *
     * @param menu - menu to update
     * @return - always return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    /**
     * Method to create delete confirmation dialog box
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Method to generate dialog about unsaved changes
     *
     * @param discardButtonClickListener - listener
     */
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        //  Create an AlertDialog.Builder and set the message, and click listeners
        //  for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        //  Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Method to signal back button has been pressed; have changes been made?
     */
    @Override
    public void onBackPressed() {
        //  If the pet hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }

        //  Otherwise if there are unsaved changes, setup a dialog to warn the user.
        //  Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };
        //  Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    /**
     * Mandatory Loader method that queries the database and fills the Cursor with the current
     * row of item information
     *
     * @param id   - row ID
     * @param args - selection args if needed
     * @return - Cursor with item information
     */
    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //  Set projection to grab all columns
        String[] projection = {TrainContract.TrainEntry._ID,
                TrainContract.TrainEntry.COLUMN_TRAIN,

                TrainContract.TrainEntry.COLUMN_DIRECTION,

                TrainContract.TrainEntry.COLUMN_NORTH_STATION,
                TrainContract.TrainEntry.COLUMN_PORTER,
                TrainContract.TrainEntry.COLUMN_BELMONT,
                TrainContract.TrainEntry.COLUMN_WAVERLEY,
                TrainContract.TrainEntry.COLUMN_WALTHAM,

                TrainContract.TrainEntry.COLUMN_BRANDEIS_ROBERTS,
                TrainContract.TrainEntry.COLUMN_KENDALL_GREEN,
                TrainContract.TrainEntry.COLUMN_HASTINGS,
                TrainContract.TrainEntry.COLUMN_SILVER_HILL,
                TrainContract.TrainEntry.COLUMN_LINCOLN,

                TrainContract.TrainEntry.COLUMN_CONCORD,
                TrainContract.TrainEntry.COLUMN_WEST_CONCORD,
                TrainContract.TrainEntry.COLUMN_SOUTH_ACTON,
                TrainContract.TrainEntry.COLUMN_LITTLETON_RTE_495,
                TrainContract.TrainEntry.COLUMN_AYER,

                TrainContract.TrainEntry.COLUMN_SHIRLEY,
                TrainContract.TrainEntry.COLUMN_NORTH_LEOMINSTER,
                TrainContract.TrainEntry.COLUMN_FITCHBURG,
                TrainContract.TrainEntry.COLUMN_WACHUSETT
        };

        return new CursorLoader(this,
                mCurrentItemUri,
                projection,
                null,
                null,
                null);
    }

    /**
     * Mandatory Loader method that reads the item data out of the Cursor and puts it into
     * the EditText fields of the EditScheduleActivity
     *
     * @param loader - Loader that filled Cursor
     * @param cursor - Cursor with item data
     */
    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {

        //  If the cursor is null or has no rows, don't proceed
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        //  If the cursor can be moved to the first row, then read the contents and
        if (cursor.moveToFirst()) {

            //  Find the columns of pet attributes that we're interested in
            int trainColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_TRAIN);
            int northStationColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_NORTH_STATION);
            int porterColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_PORTER);
            int belmontColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_BELMONT);
            int waverleyColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_WAVERLEY);

            int walthamColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_WALTHAM);
            int brandeisRobertsColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_BRANDEIS_ROBERTS);
            int kendallGreenColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_KENDALL_GREEN);
            int hastingsColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_HASTINGS);
            int silverHillColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_SILVER_HILL);

            int lincolnColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_LINCOLN);
            int concordColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_CONCORD);
            int westConcordColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_WEST_CONCORD);
            int southActonColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_SOUTH_ACTON);
            int littletonRte495ColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_LITTLETON_RTE_495);

            int ayerColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_AYER);
            int shirleyColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_SHIRLEY);
            int northLeominsterColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_NORTH_LEOMINSTER);
            int fitchburgColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_FITCHBURG);
            int wachusettColumnIndex = cursor.getColumnIndex(TrainContract.TrainEntry.COLUMN_WACHUSETT);



            //  Extract out the value from the Cursor for the given column index
            String train = cursor.getString(trainColumnIndex);
            String northStation = cursor.getString(northStationColumnIndex);
            String porter = cursor.getString(porterColumnIndex);
            String belmont = cursor.getString(belmontColumnIndex);
            String waverley = cursor.getString(waverleyColumnIndex);

            String waltham = cursor.getString(walthamColumnIndex);
            String brandeisRoberts = cursor.getString(brandeisRobertsColumnIndex);
            String kendallGreen = cursor.getString(kendallGreenColumnIndex);
            String hastings = cursor.getString(hastingsColumnIndex);
            String silverHill = cursor.getString(silverHillColumnIndex);

            String lincoln = cursor.getString(lincolnColumnIndex);
            String concord = cursor.getString(concordColumnIndex);
            String westConcord = cursor.getString(westConcordColumnIndex);
            String southActon = cursor.getString(southActonColumnIndex);
            String littletonRte495 = cursor.getString(littletonRte495ColumnIndex);

            String ayer = cursor.getString(ayerColumnIndex);
            String shirley = cursor.getString(shirleyColumnIndex);
            String northLeominster = cursor.getString(northLeominsterColumnIndex);
            String fitchburg = cursor.getString(fitchburgColumnIndex);
            String wachusett = cursor.getString(wachusettColumnIndex);


            //  Format price with dollar sign
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
//            String currentPrice = numberFormat.format(price / 100.0);

            //  Put quantity in instance variable to be used for decrement/increment button
//            mItemQuantity = quantity;

            // Update the views on the screen with the values from the database
            mTrainEditText.setText(train);
            mNorthStationEditText.setText(northStation);
            mPorterEditText.setText(porter);
            mBelmontEditText.setText(belmont);
            mWaverleyEditText.setText(waverley);

            mWalthamEditText.setText(waltham);
            mBrandeisRobertsEditText.setText(brandeisRoberts);
            mKendallGreenEditText.setText(kendallGreen);
            mHastingsEditText.setText(hastings);
            mSilverHillEditText.setText(silverHill);

            mLincolnEditText.setText(lincoln);
            mConcordEditText.setText(concord);
            mWestConcordEditText.setText(westConcord);
            mSouthActonEditText.setText(southActon);
            mLittletonRte495EditText.setText(littletonRte495);

            mAyerEditText.setText(ayer);
            mShirleyEditText.setText(shirley);
            mNorthLeominsterEditText.setText(northLeominster);
            mFitchburgEditText.setText(fitchburg);
            mWachusettEditText.setText(wachusett);
        }
    }

    /**
     * Mandatory Loader method that resets all the EditText fields
     *
     * @param loader - Loader that fills the Cursor being reset
     */
    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        mTrainEditText.setText("");
        mNorthStationEditText.setText("");
        mPorterEditText.setText("");
        mBelmontEditText.setText("");
        mWaverleyEditText.setText("");

        mWalthamEditText.setText("");
        mBrandeisRobertsEditText.setText("");
        mKendallGreenEditText.setText("");
        mHastingsEditText.setText("");
        mSilverHillEditText.setText("");

        mLincolnEditText.setText("");
        mConcordEditText.setText("");
        mWestConcordEditText.setText("");
        mSouthActonEditText.setText("");
        mLittletonRte495EditText.setText("");

        mAyerEditText.setText("");
        mShirleyEditText.setText("");
        mNorthLeominsterEditText.setText("");
        mFitchburgEditText.setText("");
        mWachusettEditText.setText("");
    }
}
