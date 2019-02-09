package com.example.justin.spinnerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrainDbHelper extends SQLiteOpenHelper {


    //  Constant to hold database version
    private static final int DATABASE_VERSION = 1;
    //  String constant to hold database name in SQLite
    private static final String DATABASE_NAME = "schedule.db";

    //  String constants used to construct the SQLite table creation String
    private static final String COMMA = ",";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String NOT_NULL = " NOT NULL";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT";
    private static final String DEFAULT_VALUE = " DEFAULT";

    //  SQLite String to create table
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TrainContract.TrainEntry.TABLE_NAME + "(" +
            TrainContract.TrainEntry._ID + INT_TYPE + PRIMARY_KEY + AUTO_INCREMENT + COMMA +
            TrainContract.TrainEntry.COLUMN_TRAIN + INT_TYPE + NOT_NULL + COMMA +

            TrainContract.TrainEntry.COLUMN_DIRECTION + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +

            TrainContract.TrainEntry.COLUMN_NORTH_STATION + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_PORTER + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_BELMONT + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_WAVERLEY + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_WALTHAM + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +

            TrainContract.TrainEntry.COLUMN_BRANDEIS_ROBERTS + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_KENDALL_GREEN + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_HASTINGS + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_SILVER_HILL + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_LINCOLN + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +

            TrainContract.TrainEntry.COLUMN_CONCORD + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_WEST_CONCORD + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_SOUTH_ACTON + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_LITTLETON_RTE_495 + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_AYER + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +

            TrainContract.TrainEntry.COLUMN_SHIRLEY + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_NORTH_LEOMINSTER + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_FITCHBURG + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" + COMMA +
            TrainContract.TrainEntry.COLUMN_WACHUSETT + INT_TYPE + NOT_NULL + DEFAULT_VALUE + " 0" +

            ");";

    //  SQLite String to delete database entries
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TrainContract.TrainEntry.TABLE_NAME;

    /**
     * Constructor for DbHelper
     *
     * @param context - context
     */
    public TrainDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method that creates SQLite database according to the String constant SQL_CREATE_ENTRIES
     *
     * @param db - database db to create table in
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Method to upgrade database version
     *
     * @param db         -  Database object to access databasae
     * @param oldVersion - Old database
     * @param newVersion - New database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  Get rid of the old database, and create a new one
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}
