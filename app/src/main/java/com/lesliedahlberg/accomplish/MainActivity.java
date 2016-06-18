package com.lesliedahlberg.accomplish;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    ListDbHelper listDbHelper;
    Cursor listCursor;
    ListCursorAdapter cursorAdapter;

    final int ADD_LIST_ITEM = 1;

    private void updateAdapter(){
        cursorAdapter.changeCursor(listDbHelper.getCursor());
        cursorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listDbHelper = ListDbHelper.getInstance(context);
        listCursor = listDbHelper.getCursor();
        cursorAdapter = new ListCursorAdapter(context, listCursor, 0);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAccomplishment.class);
                startActivityForResult(intent, ADD_LIST_ITEM, null);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ADD_LIST_ITEM) : {
                if (resultCode == Activity.RESULT_OK) {
                    updateAdapter();
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done_all) {
            listDbHelper.deleteAllListItems();
            updateAdapter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
