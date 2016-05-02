package com.one.assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.one.assignment.models.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 02-05-2016.
 */
public class DbManager extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "movie";

    private static final String TABLE_RESULT = "result";
    private static final String _ID = "_id";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_COUNT = "vote_count";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String POSTER = "poster";
    private static final String OVERVIEW = "overview";

    private static final String CREATE_TABLE_RESULT = "CREATE TABLE "
            + TABLE_RESULT + "("
            + _ID + " INTEGER PRIMARY KEY,"
            + TITLE + " TEXT,"
            + RELEASE_DATE + " TEXT,"
            + VOTE_COUNT + " INTEGER,"
            + OVERVIEW + " TEXT,"
            + POPULARITY + " REAL,"
            + POSTER + " TEXT"
            + ")";
    private static final boolean SUCCESS = true;
    private static final boolean FAIL = false;

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertAll(List<Result> results) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (!results.isEmpty()) {
            for (Result result : results) {
                ContentValues values = new ContentValues();
                values.put(TITLE, result.getTitle());
                values.put(RELEASE_DATE, result.getReleaseDate());
                values.put(VOTE_COUNT, result.getVoteCount());
                values.put(POPULARITY, result.getPopularity());
                values.put(POSTER, result.getPosterPath());
                values.put(OVERVIEW, result.getOverview());
                long x = db.insert(TABLE_RESULT, null, values);
                Log.e(TAG, "insertAll: " + x);
            }
            return SUCCESS;
        }
        return FAIL;
    }


    public ArrayList<Result> fetchAll() {
        ArrayList<Result> results = new ArrayList<Result>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESULT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Result result = new Result();
                result.setTitle(c.getString(c.getColumnIndex(TITLE)));
                result.setPopularity(c.getDouble(c.getColumnIndex(POPULARITY)));
                result.setVoteCount(c.getInt(c.getColumnIndex(VOTE_COUNT)));
                result.setPosterPath(c.getString(c.getColumnIndex(POSTER)));
                result.setReleaseDate(c.getString(c.getColumnIndex(RELEASE_DATE)));
                result.setOverview(c.getString(c.getColumnIndex(OVERVIEW)));
                results.add(result);
            } while (c.moveToNext());
        }

        return results;
    }
}
