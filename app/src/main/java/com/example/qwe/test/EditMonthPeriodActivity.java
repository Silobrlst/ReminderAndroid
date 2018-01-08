package com.example.qwe.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

public class EditMonthPeriodActivity extends AppCompatActivity {

    EditText monthDays;
    EditText week1Days;
    EditText week2Days;
    EditText week3Days;
    EditText week4Days;
    EditText week5Days;

    Intent output = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_month_period);

        monthDays = (EditText) findViewById(R.id.monthDays);
        week1Days = (EditText) findViewById(R.id.week1Days);
        week2Days = (EditText) findViewById(R.id.week2Days);
        week3Days = (EditText) findViewById(R.id.week3Days);
        week4Days = (EditText) findViewById(R.id.week4Days);
        week5Days = (EditText) findViewById(R.id.week5Days);
        Button accept = (Button)findViewById(R.id.accept);
        Button cancel = (Button)findViewById(R.id.cancel);

        String data = getIntent().getStringExtra("data");

        if(!data.isEmpty()){
            try{
                PeriodMonth periodMonth = new PeriodMonth(new JSONObject(data));

                monthDays.setText(Common.daysToString(periodMonth.getDaysOfMonth()));

                week1Days.setText(Common.daysToString(periodMonth.getWeek1().getDaysOfWeek()));
                week2Days.setText(Common.daysToString(periodMonth.getWeek2().getDaysOfWeek()));
                week3Days.setText(Common.daysToString(periodMonth.getWeek3().getDaysOfWeek()));
                week4Days.setText(Common.daysToString(periodMonth.getWeek4().getDaysOfWeek()));
                week5Days.setText(Common.daysToString(periodMonth.getWeek5().getDaysOfWeek()));

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeriodMonth periodMonth = new PeriodMonth();

                periodMonth.setDaysOfMonth(Common.parseDays(monthDays.getText().toString()));

                periodMonth.getWeek1().setDaysOfWeek(Common.parseDays(week1Days.getText().toString()));
                periodMonth.getWeek2().setDaysOfWeek(Common.parseDays(week2Days.getText().toString()));
                periodMonth.getWeek3().setDaysOfWeek(Common.parseDays(week3Days.getText().toString()));
                periodMonth.getWeek4().setDaysOfWeek(Common.parseDays(week4Days.getText().toString()));
                periodMonth.getWeek5().setDaysOfWeek(Common.parseDays(week5Days.getText().toString()));

                output.putExtra("data", periodMonth.toJson().toString());
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
