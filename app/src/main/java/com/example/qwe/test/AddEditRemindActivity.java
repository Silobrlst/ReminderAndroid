package com.example.qwe.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import org.json.JSONObject;

public class AddEditRemindActivity extends AppCompatActivity {

    TimePicker timePicker;
    EditText text;

    RemindsContainer remindsContainer;
    String remindId;
    String data;

    Remind remind = new Remind();

    Intent output = new Intent();

    Button choosePeriod;

    AlertDialog.Builder choosePeriodDialogBuilder;
    AlertDialog.Builder confirmDialogBuilder;

    int requestedCodeTemp = -1;

    Intent addEditPeriodIntent;

    CharSequence items[] = {"Изменить...", "Разово", "Неделя", "Месяц", "Год"};

    private static final int changeResCode = 0;
    private static final int editOneTimePeriodResCode = 1;
    private static final int editWeekPeriodResCode = 2;
    private static final int editMonthPeriodResCode = 3;
    private static final int editYearPeriodResCode = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_remind);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button accept = (Button) findViewById(R.id.accept);
        Button cancel = (Button) findViewById(R.id.cancel);
        choosePeriod = (Button) findViewById(R.id.choosePeriodButton);
        text = (EditText) findViewById(R.id.text);

        remindsContainer = (RemindsContainer)getIntent().getSerializableExtra("remindsContainer");
        remindId = getIntent().getStringExtra("remindId");
        data = getIntent().getStringExtra("data");

        timePicker.setIs24HourView(true);

        //<заполняем интерфейс>=====================================================================
        if(!data.isEmpty()){
            try{
                remind.fromJson(new JSONObject(data));
                remind.setId(remindId);

                text.setText(remind.getText());
                timePicker.setCurrentMinute(remind.getMinute());
                timePicker.setCurrentHour(remind.getHour());

                if(remind.getPeriod() != null){
                    remindSetPeriod(remind.getPeriod());
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        //</заполняем интерфейс>====================================================================

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data.isEmpty()){
                    remind.setText(text.getText().toString());
                    remind.setTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());

                }else{
                    remind = new Remind();

                    remind.setText(text.getText().toString());
                    remind.setTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                }

                output.putExtra("remindId", remind.getId());
                output.putExtra("data", remind.toJson().toString());
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

        //<диалог выбора периода>===================================================================
        choosePeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePeriodDialogBuilder.create().show();
            }
        });

        choosePeriodDialogBuilder = new AlertDialog.Builder(AddEditRemindActivity.this);
        choosePeriodDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case changeResCode:
                        switch(remind.getPeriod().getPeriodType()){
                            case OneTime:
                                openEditPeriodActivity(EditOneTimePeriodActivity.class, editOneTimePeriodResCode, true);
                                break;
                            case Week:
                                openEditPeriodActivity(EditWeekPeriodActivity.class, editWeekPeriodResCode, true);
                                break;
                            case Month:
                                openEditPeriodActivity(EditMonthPeriodActivity.class, editMonthPeriodResCode, true);
                                break;
                            case Year:
                                openEditPeriodActivity(EditYearPeriodActivity.class, editYearPeriodResCode, true);
                                break;
                        }
                        break;
                    case editOneTimePeriodResCode:
                        openEditPeriodActivity(EditOneTimePeriodActivity.class, which, false);
                        break;
                    case editWeekPeriodResCode:
                        openEditPeriodActivity(EditWeekPeriodActivity.class, which, false);
                        break;
                    case editMonthPeriodResCode:
                        openEditPeriodActivity(EditMonthPeriodActivity.class, which, false);
                        break;
                    case editYearPeriodResCode:
                        openEditPeriodActivity(EditYearPeriodActivity.class, which, false);
                        break;
                }
            }
        });
        //</диалог выбора периода>==================================================================

        //<диалог подтверждения создания нового пероида>============================================
        confirmDialogBuilder = new AlertDialog.Builder(AddEditRemindActivity.this);
        confirmDialogBuilder.setTitle("Создать новый период?");
        confirmDialogBuilder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addEditPeriodIntent.putExtra("data", remind.getPeriod().toJson().toString());
                startActivityForResult(addEditPeriodIntent, requestedCodeTemp);
            }
        });
        confirmDialogBuilder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //</диалог подтверждения создания нового пероида>===========================================
    }

    private void openEditPeriodActivity(Class<?> classIn, int requestCodeIn, boolean changePeriodIn){
        addEditPeriodIntent = new Intent(AddEditRemindActivity.this, classIn);

        if(remind.getPeriod() != null){
            if(changePeriodIn){
                addEditPeriodIntent.putExtra("data", remind.getPeriod().toJson().toString());
                startActivityForResult(addEditPeriodIntent, requestCodeIn);
            }else{
                requestedCodeTemp = requestCodeIn;
                confirmDialogBuilder.create().show();
            }
        }else{
            addEditPeriodIntent.putExtra("data", "");
            startActivityForResult(addEditPeriodIntent, requestCodeIn);
        }
    }

    private void remindSetPeriod(Period periodIn){
        remind.setPeriod(periodIn);

        switch(periodIn.getPeriodType()){
            case OneTime:
                choosePeriod.setText("Разово...");
                break;
            case Week:
                choosePeriod.setText("Неделя...");
                break;
            case Month:
                choosePeriod.setText("Месяц...");
                break;
            case Year:
                choosePeriod.setText("Год...");
                break;
        }
    }
    private void remindSetPeriod(JSONObject periodJsonIn){
        remind.setPeriod(periodJsonIn);

        switch(remind.getPeriod().getPeriodType()){
            case OneTime:
                choosePeriod.setText("Разово...");
                break;
            case Week:
                choosePeriod.setText("Неделя...");
                break;
            case Month:
                choosePeriod.setText("Месяц...");
                break;
            case Year:
                choosePeriod.setText("Год...");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            try{
                JSONObject json = new JSONObject(data.getExtras().getString("data"));

                switch(requestCode){
                    case editOneTimePeriodResCode:
                        remindSetPeriod(new PeriodOneTime(json));
                        break;
                    case editWeekPeriodResCode:
                        remindSetPeriod(new PeriodWeek(json));
                        break;
                    case editMonthPeriodResCode:
                        remindSetPeriod(new PeriodMonth(json));
                        break;
                    case editYearPeriodResCode:
                        remindSetPeriod(new PeriodYear(json));
                        break;
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
