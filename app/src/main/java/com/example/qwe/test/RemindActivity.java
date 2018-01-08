package com.example.qwe.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RemindActivity extends AppCompatActivity {

    Intent output = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

        EditText text = (EditText) findViewById(R.id.text);
        Button accept = (Button) findViewById(R.id.accept);

        String textStr = getIntent().getStringExtra("text");

        text.setText(textStr);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }
}
