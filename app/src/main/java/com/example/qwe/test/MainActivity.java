package com.example.qwe.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RemindsContainer remindsContainer;

    Intent reminder;

    Loader loader = new Loader();

    AlertDialog.Builder remindDialogBuilder;

    int selectedPos = 0;

    int addRemindRequestCode = 0;
    int editRemindRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Напоминалка");

        ListView remindsList = (ListView) findViewById(R.id.remindsList);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button openConfigButton = (Button) findViewById(R.id.openConfigButton);

        remindsContainer = new RemindsContainer(this);
        remindsList.setAdapter(remindsContainer);

        loader.loadReminds(this, remindsContainer);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEditRemindActivity = new Intent(MainActivity.this, AddEditRemindActivity.class);
                addEditRemindActivity.putExtra("remindId", "0"); //0 - создаем новую напоминалку
                addEditRemindActivity.putExtra("data", "");

                startActivityForResult(addEditRemindActivity, addRemindRequestCode);
            }
        });

        openConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configEditor = new Intent(MainActivity.this, ConfigEditor.class);
                startActivity(configEditor);
            }
        });

        //<диалог действий над напоминанием>========================================================
        remindDialogBuilder = new AlertDialog.Builder(this);
        remindDialogBuilder.setPositiveButton("Изменить...", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Remind remind = remindsContainer.getItem(selectedPos);

                Intent addEditRemindActivity = new Intent(MainActivity.this, AddEditRemindActivity.class);
                addEditRemindActivity.putExtra("remindId", remind.getId());
                addEditRemindActivity.putExtra("data", remind.toJson().toString());

                startActivityForResult(addEditRemindActivity, editRemindRequestCode);
            }
        });
        remindDialogBuilder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Remind remind = remindsContainer.getItem(selectedPos);
                loader.removeRemindById(MainActivity.this, remind.getId());
                remindsContainer.remove(remind);
            }
        });
        remindDialogBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        //</диалог действий над напоминанием>=======================================================

        remindsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                remindDialogBuilder.create().show();
            }
        });

        reminder = new Intent(this, Reminder.class);

        this.startService(reminder);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String id = data.getExtras().getString("remindId");
            String remindJsonStr = data.getExtras().getString("data");

            try {
                if (requestCode == addRemindRequestCode) {
                    Remind remind = new Remind(id, new JSONObject(remindJsonStr));
                    remindsContainer.add(remind);
                    loader.saveRemindOnly(MainActivity.this, remind);

                } else if (requestCode == editRemindRequestCode) {
                    Remind remind = remindsContainer.getRemindById(id);
                    JSONObject jsonObject = new JSONObject(remindJsonStr);
                    remind.fromJson(jsonObject);
                    loader.saveRemindOnly(MainActivity.this, remind);
                }

                remindsContainer.notifyDataSetChanged();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
