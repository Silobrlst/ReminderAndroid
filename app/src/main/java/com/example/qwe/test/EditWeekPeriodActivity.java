package com.example.qwe.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import org.json.JSONObject;

import java.util.ArrayList;

public class EditWeekPeriodActivity extends AppCompatActivity {
    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;
    CheckBox sunday;

    Intent output = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_week_period);

        monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
        wednesday = (CheckBox) findViewById(R.id.wednesday);
        thursday = (CheckBox) findViewById(R.id.thursday);
        friday = (CheckBox) findViewById(R.id.friday);
        saturday = (CheckBox) findViewById(R.id.saturday);
        sunday = (CheckBox) findViewById(R.id.sunday);
        Button accept = (Button)findViewById(R.id.accept);
        Button cancel = (Button)findViewById(R.id.cancel);

        String data = getIntent().getStringExtra("data");

        if(!data.isEmpty()){
            try{
                PeriodWeek periodWeek = new PeriodWeek(new JSONObject(data));

                monday.setChecked(false);
                tuesday.setChecked(false);
                wednesday.setChecked(false);
                thursday.setChecked(false);
                friday.setChecked(false);
                saturday.setChecked(false);
                sunday.setChecked(false);

                for(int day: periodWeek.getDaysOfWeek()){
                    switch (day){
                        case 1:
                            monday.setChecked(true);
                            break;
                        case 2:
                            tuesday.setChecked(true);
                            break;
                        case 3:
                            wednesday.setChecked(true);
                            break;
                        case 4:
                            thursday.setChecked(true);
                            break;
                        case 5:
                            friday.setChecked(true);
                            break;
                        case 6:
                            saturday.setChecked(true);
                            break;
                        case 7:
                            sunday.setChecked(true);
                            break;
                    }
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> days = new ArrayList<>();

                if(monday.isChecked()) days.add(1);
                if(tuesday.isChecked()) days.add(2);
                if(wednesday.isChecked()) days.add(3);
                if(thursday.isChecked()) days.add(4);
                if(friday.isChecked()) days.add(5);
                if(saturday.isChecked()) days.add(6);
                if(sunday.isChecked()) days.add(7);

                PeriodWeek periodWeek = new PeriodWeek();
                periodWeek.setDaysOfWeek(days);

                output.putExtra("data", periodWeek.toJson().toString());
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
