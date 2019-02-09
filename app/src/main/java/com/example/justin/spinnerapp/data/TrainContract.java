package com.example.justin.spinnerapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class TrainContract {

    public static final String CONTENT_AUTHORITY = "com.example.justin.spinnerapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRAINS = "trains";

    /**
     *  Inner class of Inventory contract constants
     */
    public static final class TrainEntry implements BaseColumns {
        //  MIME list type
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_TRAINS;

        //  MIME item type
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_TRAINS;

        //  Content URI for accessing database:  content://com.example.justin.inventoryappone/inventory
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRAINS);

        //  Table name
        public static final String TABLE_NAME = "inventory";

        //  Database columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TRAIN = "train";
        public static final String COLUMN_DIRECTION = "direction";
        public static final String COLUMN_NORTH_STATION = "northstation";
        public static final String COLUMN_PORTER = "porter";
        public static final String COLUMN_BELMONT = "belmont";
        public static final String COLUMN_WAVERLEY ="waverley";
        public static final String COLUMN_WALTHAM = "waltham";
        public static final String COLUMN_BRANDEIS_ROBERTS = "brandeisroberts";
        public static final String COLUMN_KENDALL_GREEN = "kendellgreen";
        public static final String COLUMN_HASTINGS = "hastings";
        public static final String COLUMN_SILVER_HILL = "silverhill";
        public static final String COLUMN_LINCOLN ="lincoln";
        public static final String COLUMN_CONCORD = "concord";
        public static final String COLUMN_WEST_CONCORD = "westconcord";
        public static final String COLUMN_SOUTH_ACTON = "southacton";
        public static final String COLUMN_LITTLETON_RTE_495 = "littletonrte495";
        public static final String COLUMN_AYER = "ayer";
        public static final String COLUMN_SHIRLEY ="shirley";
        public static final String COLUMN_NORTH_LEOMINSTER = "northleominster";
        public static final String COLUMN_FITCHBURG = "fitchburg";
        public static final String COLUMN_WACHUSETT = "wachusett";

    }

}
