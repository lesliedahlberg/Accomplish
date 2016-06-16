package com.lesliedahlberg.accomplish;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAccomplishment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accomplishment);
        Button addButton = (Button) findViewById(R.id.addButton);
        final EditText textField = (EditText) findViewById(R.id.editText);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.addAccomplishment(textField.getText().toString());
                finish();
            }
        });
    }
}
