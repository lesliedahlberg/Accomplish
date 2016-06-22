package com.lesliedahlberg.accomplish;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lesliedahlberg on 17/06/16.
 */
public class ListCursorAdapter extends CursorAdapter {

    Animation animation;

    public ListCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {

        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
        deleteButton.setTag(cursor.getInt(cursor.getColumnIndexOrThrow(ListDbHelper.KEY_LIST_ID)));

        /*deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (Integer) v.getTag();
                ListDbHelper listDbHelper = ListDbHelper.getInstance(context);
                listDbHelper.deleteListItem(id);
                changeCursor(listDbHelper.getCursor());
                notifyDataSetChanged();
            }
        });*/

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Toast.makeText(context, "Task finished!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int id = (Integer) v.getTag();
                        ListDbHelper listDbHelper = ListDbHelper.getInstance(context);
                        listDbHelper.deleteListItem(id);
                        changeCursor(listDbHelper.getCursor());
                        notifyDataSetChanged();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);
            }
        });

        //TextView idField = (TextView) view.findViewById(R.id.idField);
        TextView accomplishmentField = (TextView) view.findViewById(R.id.accomplishmentField);
        TextView dateField = (TextView) view.findViewById(R.id.dateField);

        //idField.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ListDbHelper.KEY_LIST_ID))));
        accomplishmentField.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListDbHelper.KEY_LIST_TITLE)));

        String dateValue = cursor.getString(cursor.getColumnIndexOrThrow(ListDbHelper.KEY_LIST_CREATION_DATE));
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = (Date)formatter.parse(dateValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String timeAgo = TimeAgo.DateDifference(date.getTime());

        dateField.setText(timeAgo);


       animation = AnimationUtils.loadAnimation(context, R.anim.fade_anim);
    }
}
