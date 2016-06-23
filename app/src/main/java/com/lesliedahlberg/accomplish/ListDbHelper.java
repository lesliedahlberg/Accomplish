package com.lesliedahlberg.accomplish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lesliedahlberg on 17/06/16.
 */
public class ListDbHelper extends SQLiteOpenHelper{
    private static ListDbHelper sInstance;

    public static synchronized ListDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ListDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    private static final String DATABASE_NAME = "Database";
    private static final int DATABASE_VERSION = 8;

    public static final String TABLE_LIST = "list";

    public static final String KEY_LIST_ID = "_id";
    public static final String KEY_LIST_TITLE = "title";
    public static final String KEY_LIST_DESCRIPTION = "description";
    public static final String KEY_LIST_CREATION_DATE = "creation_date";

    private ListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addListItem(ListItem listItem) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_TITLE, listItem.title);
            long date = System.currentTimeMillis() / 1000;
            values.put(KEY_LIST_CREATION_DATE, date);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_LIST, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to add list item to database");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteListItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_LIST, KEY_LIST_ID + " = " + String.valueOf(id), null);
            Log.d("TAG", "DELETED successfully");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to delete list item");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteAllListItems() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_LIST, null, null);
            Log.d("TAG", "DELETED successfully");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to delete list item");
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getCursor(){
        String LIST_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_LIST);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(LIST_SELECT_QUERY, null);
        return cursor;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_LIST +
                "(" +
                KEY_LIST_ID + " INTEGER PRIMARY KEY," +
                KEY_LIST_TITLE + " TEXT, " +
                KEY_LIST_DESCRIPTION + " TEXT, " +
                KEY_LIST_CREATION_DATE + " INTEGER" +
                ")";
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
            onCreate(db);
        }
    }


}
