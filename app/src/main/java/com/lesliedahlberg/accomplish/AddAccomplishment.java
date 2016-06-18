package com.lesliedahlberg.accomplish;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddAccomplishment extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_accomplishment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button addButton = (Button) findViewById(R.id.addButton);
        final EditText textField = (EditText) findViewById(R.id.editText);
        final Context context = this;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDbHelper listDbHelper = ListDbHelper.getInstance(context);
                ListItem listItem = new ListItem();
                listItem.title = textField.getText().toString();
                listDbHelper.addListItem(listItem);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
