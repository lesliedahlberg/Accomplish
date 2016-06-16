package com.lesliedahlberg.accomplish;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lesliedahlberg on 16/06/16.
 */
public class AccomplishmentDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Accomplishments.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AccomplishmentContract.AccomplishmentEntry.TABLE_NAME + " (" +
                    AccomplishmentContract.AccomplishmentEntry.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY," +
                    AccomplishmentContract.AccomplishmentEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AccomplishmentContract.AccomplishmentEntry.TABLE_NAME;

    public AccomplishmentDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
