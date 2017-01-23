package com.urvesh.android_arch_mvp.provider;

import android.content.ContentProvider;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.urvesh.android_arch_mvp.domain.Constants;

public abstract class BaseProvider extends ContentProvider {
    protected SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String SQL_CREATE_FAVORITE =
                "CREATE TABLE " + FavoriteProvider.FavoriteColumns.TABLE_NAME + " (" +
                        FavoriteProvider.FavoriteColumns._ID + " INTEGER PRIMARY KEY, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_ID + " TEXT, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_CATEGORY + " TEXT, " +

                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_NAME + " TEXT, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_IMAGE_URL + " TEXT, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SUBCATEGORY_ID + " INTEGER, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SUBCATEGORY + " TEXT, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_UNIT_ID + " INTEGER, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_UNIT + " TEXT, " +

                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_CATEGORY_ID + " INTEGER, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SELECTED + " INTEGER, " +
                        FavoriteProvider.FavoriteColumns.COLUMN_IS_ACTIVE + " INTEGER );";

        private static final String SQL_DELETE_FAVORITE = " DROP TABLE IF EXISTS " + FavoriteProvider.FavoriteColumns.TABLE_NAME;


        private DatabaseHelper(Context context) {
            super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v("db", "created");
            db.execSQL(SQL_CREATE_FAVORITE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.v("db", "updated");
            if (oldVersion > 0) {
                db.execSQL(SQL_DELETE_FAVORITE);
            }
            onCreate(db);
        }
    }
}