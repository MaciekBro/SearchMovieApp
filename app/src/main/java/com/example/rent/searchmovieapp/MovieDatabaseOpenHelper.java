package com.example.rent.searchmovieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by RENT on 2017-04-01.
 */

public class MovieDatabaseOpenHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "my_movies.db";
    //    private static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE MOVIES (TITLE TEXT, YEAR TEXT, POSTER TEXT, TYPE TEXT)";
    private static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "
            + MovieTableContract.TABLE_NAME + " ("
            + MovieTableContract.COLUMN_TITLE + " TEXT, "
            + MovieTableContract.COLUMN_TYPE + " TEXT, "
            + MovieTableContract.COLUMN_POSTER + " TEXT, "
            + MovieTableContract.COLUMN_YEAR + " TEXT)";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + MovieTableContract.TABLE_NAME;

    public MovieDatabaseOpenHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {   //te parametry zakomentowane podajemy w prost
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL(SQL_DROP_TABLE);
            onCreate(db);
        }
    }
}
