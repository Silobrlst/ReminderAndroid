package com.example.qwe.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;

import org.json.JSONObject;

public class EditOneTimePeriodActivity extends AppCompatActivity {
    DatePicker datePicker;
    CheckBox selfDestruct;

    Intent output = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_one_time_period);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        selfDestruct = (CheckBox)findViewById(R.id.selfDestruct);
        Button accept = (Button)findViewById(R.id.accept);
        Button cancel = (Button)findViewById(R.id.cancel);

        String data = getIntent().getStringExtra("data");

        if(!data.isEmpty()){
            try{
                PeriodOneTime periodOneTime = new PeriodOneTime(new JSONObject(data));

                datePicker.updateDate(periodOneTime.getYear(), periodOneTime.getMonth(), periodOneTime.getDay());
                selfDestruct.setChecked(periodOneTime.getSelfDestruction());

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeriodOneTime periodOneTime = new PeriodOneTime();
                periodOneTime.setDay(datePicker.getDayOfMonth());
                periodOneTime.setMonth(datePicker.getMonth());
                periodOneTime.setYear(datePicker.getYear());

                periodOneTime.setSelfDestruction(selfDestruct.isChecked());

                output.putExtra("data", periodOneTime.toJson().toString());
                setResult(RESULT_OK, output);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED, output);
                finish();
            }
        });
    }
}
