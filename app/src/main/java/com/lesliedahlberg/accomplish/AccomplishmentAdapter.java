package com.lesliedahlberg.accomplish;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by lesliedahlberg on 16/06/16.
 */
public class AccomplishmentAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    AccomplishmentDbHelper mDbHelper;
    SQLiteDatabase db;
    ArrayList<String> items;
    String[] projection = {
            AccomplishmentContract.AccomplishmentEntry.COLUMN_NAME_ENTRY_ID,
            AccomplishmentContract.AccomplishmentEntry.COLUMN_NAME_TITLE
    };
    String sortOrder =
            AccomplishmentContract.AccomplishmentEntry.COLUMN_NAME_ENTRY_ID + " ASC";
    Cursor c;

    public AccomplishmentAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDbHelper = new AccomplishmentDbHelper(context);
        db = mDbHelper.getReadableDatabase();
        readDb();
    }

    public void readDb(){
        c = db.query(
                AccomplishmentContract.AccomplishmentEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        c.moveToFirst();
    }

    @Override
    public int getCount() {
        //return items.size();
        return c.getCount();
    }

    @Override
    public Object getItem(int position) {
        //return items.get(position);
        c.moveToPosition(position);
        return c.getString(1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.accomplishment_list, null);
        TextView numberField = (TextView) rowView.findViewById(R.id.numberField);
        TextView accomplishmentField = (TextView) rowView.findViewById(R.id.accomplishmentField);
        numberField.setText(String.valueOf(position+1));
        accomplishmentField.setText((String) getItem(position));
        return rowView;
    }
}
