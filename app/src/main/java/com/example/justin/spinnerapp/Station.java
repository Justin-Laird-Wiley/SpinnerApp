package com.example.justin.spinnerapp;

public class Station {

    private String mStationName;
    private String mRailLine;
    private String mDbColumnName;

    public Station(String station, String railLine, String columnName) {
        mStationName = station;
        mRailLine =railLine;
        mDbColumnName = columnName;
    }

    public String getmStationName() {
        return mStationName;
    }

    public String getmRailLine() {
        return mRailLine;
    }

    public String getmDbColumnName() {
        return mDbColumnName;
    }

    @Override
    public String toString() {
        return getmStationName();
    }

}
