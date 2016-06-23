package com.lesliedahlberg.accomplish;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddAccomplishment extends AppCompatActivity {

    EditText textField;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_accomplishment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button addButton = (Button) findViewById(R.id.addButton);
        textField = (EditText) findViewById(R.id.editText);
        context = this;
        textField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clickDone();

                    handled = true;
                }
                return handled;
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDone();
            }
        });
    }

    private void clickDone(){
        ListDbHelper listDbHelper = ListDbHelper.getInstance(context);
        ListItem listItem = new ListItem();
        listItem.title = textField.getText().toString();
        listDbHelper.addListItem(listItem);
        setResult(Activity.RESULT_OK);
        finish();
    }
}
