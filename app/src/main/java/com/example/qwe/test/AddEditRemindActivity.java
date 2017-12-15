package com.example.qwe.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddEditRemindActivity extends AppCompatActivity {

    TimePicker timePicker;
    EditText text;

    RemindsContainer remindsContainer;
    String remindId;

    Loader loader = new Loader();

    Remind remind = new Remind();

    Intent output = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_remind);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button accept = (Button) findViewById(R.id.acceptButton);
        Button cancel = (Button) findViewById(R.id.cancelButton);
        text = (EditText) findViewById(R.id.text);

        remindsContainer = (RemindsContainer)getIntent().getSerializableExtra("remindsContainer");
        remindId = getIntent().getStringExtra("remindId");

        timePicker.setIs24HourView(true);

        //<заполняем интерфейс>=====================================================================
        if(!remindId.equals("0")){
            loader.loadRemindById(this, remindId, remind);

            text.setText(remind.getText());
            timePicker.setCurrentMinute(remind.getMinute());
            timePicker.setCurrentHour(remind.getHour());
        }
        //</заполняем интерфейс>====================================================================

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!remindId.equals("0")){
                    remind.setText(text.getText().toString());
                    remind.setTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());

                }else{
                    remind = new Remind();

                    remind.setText(text.getText().toString());
                    remind.setTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                }

                loader.saveRemindOnly(AddEditRemindActivity.this, remind);

                output.putExtra("remindId", remind.getId());
                setResult(RESULT_OK, output);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, output);
                finish();
            }
        });
    }
}
