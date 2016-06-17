package com.lesliedahlberg.accomplish;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lesliedahlberg on 17/06/16.
 */
public class ListCursorAdapter extends CursorAdapter {

    public ListCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
        deleteButton.setTag(cursor.getInt(cursor.getColumnIndexOrThrow(ListDbHelper.KEY_LIST_ID)));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (Integer) v.getTag();
                ListDbHelper listDbHelper = ListDbHelper.getInstance(context);
                listDbHelper.deleteListItem(id);
                changeCursor(listDbHelper.getCursor());
                notifyDataSetChanged();
            }
        });

        //TextView idField = (TextView) view.findViewById(R.id.idField);
        TextView accomplishmentField = (TextView) view.findViewById(R.id.accomplishmentField);

        //idField.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ListDbHelper.KEY_LIST_ID))));
        accomplishmentField.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListDbHelper.KEY_LIST_TITLE)));
    }
}
