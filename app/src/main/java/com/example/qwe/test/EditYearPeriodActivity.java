package com.example.qwe.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

public class EditYearPeriodActivity extends AppCompatActivity {

    EditText yearDays;

    EditText janDays;
    EditText febDays;
    EditText marDays;
    EditText aprDays;
    EditText mayDays;
    EditText junDays;
    EditText julDays;
    EditText augDays;
    EditText sepDays;
    EditText octDays;
    EditText novDays;
    EditText decDays;

    Intent output = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_year_period);

        yearDays = (EditText) findViewById(R.id.yearDays);

        janDays = (EditText) findViewById(R.id.janDays);
        febDays = (EditText) findViewById(R.id.febDays);
        marDays = (EditText) findViewById(R.id.marDays);
        aprDays = (EditText) findViewById(R.id.aprDays);
        mayDays = (EditText) findViewById(R.id.mayDays);
        junDays = (EditText) findViewById(R.id.junDays);
        julDays = (EditText) findViewById(R.id.julDays);
        augDays = (EditText) findViewById(R.id.augDays);
        sepDays = (EditText) findViewById(R.id.sepDays);
        octDays = (EditText) findViewById(R.id.octDays);
        novDays = (EditText) findViewById(R.id.novDays);
        decDays = (EditText) findViewById(R.id.decDays);

        Button accept = (Button)findViewById(R.id.accept);
        Button cancel = (Button)findViewById(R.id.cancel);

        String data = getIntent().getStringExtra("data");

        if(!data.isEmpty()){
            try{
                PeriodYear periodYear = new PeriodYear(new JSONObject(data));

                yearDays.setText(Common.daysToString(periodYear.getDaysOfYear()));

                janDays.setText(Common.daysToString(periodYear.getJan().getDaysOfMonth()));
                febDays.setText(Common.daysToString(periodYear.getFeb().getDaysOfMonth()));
                marDays.setText(Common.daysToString(periodYear.getMar().getDaysOfMonth()));
                aprDays.setText(Common.daysToString(periodYear.getApr().getDaysOfMonth()));

                mayDays.setText(Common.daysToString(periodYear.getMay().getDaysOfMonth()));
                junDays.setText(Common.daysToString(periodYear.getJun().getDaysOfMonth()));
                julDays.setText(Common.daysToString(periodYear.getJul().getDaysOfMonth()));
                augDays.setText(Common.daysToString(periodYear.getAug().getDaysOfMonth()));

                sepDays.setText(Common.daysToString(periodYear.getSep().getDaysOfMonth()));
                octDays.setText(Common.daysToString(periodYear.getOct().getDaysOfMonth()));
                novDays.setText(Common.daysToString(periodYear.getNov().getDaysOfMonth()));
                decDays.setText(Common.daysToString(periodYear.getDec().getDaysOfMonth()));

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeriodYear periodYear = new PeriodYear();

                periodYear.setDaysOfYear(Common.parseDays(yearDays.getText().toString()));

                periodYear.getJan().setDaysOfMonth(Common.parseDays(janDays.getText().toString()));
                periodYear.getFeb().setDaysOfMonth(Common.parseDays(febDays.getText().toString()));
                periodYear.getMar().setDaysOfMonth(Common.parseDays(marDays.getText().toString()));
                periodYear.getApr().setDaysOfMonth(Common.parseDays(aprDays.getText().toString()));

                periodYear.getMay().setDaysOfMonth(Common.parseDays(mayDays.getText().toString()));
                periodYear.getJun().setDaysOfMonth(Common.parseDays(junDays.getText().toString()));
                periodYear.getJul().setDaysOfMonth(Common.parseDays(julDays.getText().toString()));
                periodYear.getAug().setDaysOfMonth(Common.parseDays(augDays.getText().toString()));

                periodYear.getSep().setDaysOfMonth(Common.parseDays(sepDays.getText().toString()));
                periodYear.getOct().setDaysOfMonth(Common.parseDays(octDays.getText().toString()));
                periodYear.getNov().setDaysOfMonth(Common.parseDays(novDays.getText().toString()));
                periodYear.getDec().setDaysOfMonth(Common.parseDays(decDays.getText().toString()));

                output.putExtra("data", periodYear.toJson().toString());
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
